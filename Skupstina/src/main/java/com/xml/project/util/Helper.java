package com.xml.project.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.xml.project.jaxb.Dokument;

public class Helper {

	private TransformerFactory transformerFactory;

	private DatabaseClient databaseClient;
	private DatabaseUtil dUtil = new DatabaseUtil();
	private Marshaller m;
	public JAXBContext context;
	static XMLDocumentManager xmlMenager;
	static Unmarshaller unmarshaller;
	static String XML_FILE = "data/";

	private static final String XSLT_FILE = XML_FILE + "grddl.xsl";

	@SuppressWarnings("restriction")
	public Helper() throws JAXBException {

		/* INIT DATABASE CLIENT */
		databaseClient = DatabaseClientFactory.newClient(dUtil.getHost(), dUtil.getPort(), dUtil.getDatabase(),
				dUtil.getUsername(), dUtil.getPassword(), dUtil.getAuthType());

		xmlMenager = databaseClient.newXMLDocumentManager();

		context = JAXBContext.newInstance("com.xml.project.jaxb");

		m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		unmarshaller = context.createUnmarshaller();

		transformerFactory = new TransformerFactoryImpl();
	}

	public void generateRDF(String docId)
			throws SAXException, JAXBException, FileNotFoundException, TransformerException {

		String doc = "/acts/decisions/" + docId + ".xml";
		Dokument dokument = null;

		DOMHandle content = new DOMHandle();
		xmlMenager.read(doc, content);

		Document docc = content.get();

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "skupstina.xsd"));

		unmarshaller.setSchema(schema);
		dokument = (Dokument) unmarshaller.unmarshal(docc);

		// save to xml file
		File saveFile = new File(XML_FILE + docId + ".xml");
		m.marshal(dokument, saveFile);

		// convert to RDF file
		StreamSource transformSource = new StreamSource(new File(XSLT_FILE));
		// Initialize GRDDL transformer object
		Transformer grddlTransformer = transformerFactory.newTransformer(transformSource);

		// Set the indentation properties
		grddlTransformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		grddlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

		// Initialize transformation subject
		StreamSource source = new StreamSource(new FileInputStream(saveFile));

		// Initialize result stream
		StreamResult result = new StreamResult(new FileOutputStream(XML_FILE + docId + ".rdf"));

		// Trigger the transformation
		grddlTransformer.transform(source, result);
		
		//delete xml file
		saveFile.delete();
	}
}
