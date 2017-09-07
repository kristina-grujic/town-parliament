package com.xml.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.DocumentMetadataHandle.DocumentCollections;
import com.xml.project.jaxb.Amandman;
import com.xml.project.jaxb.Clan;
import com.xml.project.jaxb.Dokument;
import com.xml.project.jaxb.DopunaZakona;
import com.xml.project.jaxb.DopunaZakonaAmandamana;
import com.xml.project.jaxb.Glava;
import com.xml.project.jaxb.Propisi;
import com.xml.project.util.DatabaseUtil;

public class AcceptAmandman {

	private static DatabaseClient databaseClient;
	private static DatabaseUtil dUtil = new DatabaseUtil();
	private static Marshaller m;
	public static JAXBContext context;
	static XMLDocumentManager xmlMenager;
	static Unmarshaller unmarshaller;
	static String XML_FILE = "data/";

	static {
		try {
			databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
					dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());

			xmlMenager = databaseClient.newXMLDocumentManager();

			context = JAXBContext.newInstance("com.xml.project.jaxb");

			m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
		}
	}

	//Treba implementirati nekako
	public static String Accept(String docId) throws SAXException, JAXBException, IOException {

		Amandman amandman = null;

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "amandman.xsd"));

		unmarshaller.setSchema(schema);

		DOMHandle content = new DOMHandle();

		String doc = "/amandman/decisions/" + docId + ".xml";

		xmlMenager.read(doc, content);

		Document docc = content.get();
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		amandman = (Amandman) unmarshaller.unmarshal(docc);

		Dokument dokument = null;

		SchemaFactory schemaFactory1 = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema1 = schemaFactory1.newSchema(new File(XML_FILE + "skupstina.xsd"));

		unmarshaller.setSchema(schema1);

		DOMHandle content1 = new DOMHandle();

		String doc1 = "/acts/decisions/" + amandman.getLinkAkta() + ".xml";

		xmlMenager.read(doc1, content1);

		Document docc1 = content1.get();
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		dokument = (Dokument) unmarshaller.unmarshal(docc1);

		List<Propisi> doku = dokument.getPropisi();
		for(Propisi pr: doku) {
			pr.setDopunaZakona(amandman.getDopunaZakonaAmandmana());
		}

		dokument.setPropisi(doku);


		File f = new File(XML_FILE + "/dopuna.xml");
		FileOutputStream out = new FileOutputStream(f);
		m.marshal(dokument, out);



		//Brise postojeci akt
		ActController.deleteCollection(amandman.getLinkAkta());


		//Dodaje izmenjenu verziju
		xmlMenager = databaseClient.newXMLDocumentManager();
		DocumentUriTemplate template = xmlMenager.newDocumentUriTemplate("xml");
		template.setDirectory("/acts/decisions/");

		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE + "dopuna.xml"));
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add("/parliament/acts/accepted");

		DocumentDescriptor desc = xmlMenager.create(template, metadata, handle);

		XMLDocumentManager xmlMenager1 = databaseClient.newXMLDocumentManager();
		DocumentMetadataHandle metadata1 = new DocumentMetadataHandle();
		xmlMenager1.readMetadata(doc, metadata1);

		DocumentCollections collections = metadata1.getCollections();
		collections.remove("/parliament/amandman/proposed");
		collections.add("/parliament/amandman/accepted");
		xmlMenager1.writeMetadata(doc, metadata1);

		return null;
	}
}
