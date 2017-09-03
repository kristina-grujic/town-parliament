package com.xml.project.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.GenericDocumentManager;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.DocumentMetadataHandle.DocumentCollections;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.xml.project.dto.MesagesDTO;
import com.xml.project.dto.SearchDTO;
import com.xml.project.dto.VoteDTO;
import com.xml.project.jaxb.Dokument;
import com.xml.project.model.Published;
import com.xml.project.model.User;
import com.xml.project.model.Voting;
import com.xml.project.repository.PublishedRepository;
import com.xml.project.repository.VotingRepository;
import com.xml.project.service.UserService;
import com.xml.project.util.DatabaseUtil;
import com.xml.project.util.Helper;

@RestController
@RequestMapping(value = "api/act")
public class ActController {

	@Autowired
	UserService userService;

	@Autowired
	PublishedRepository publishedRepository;

	@Autowired
	VotingRepository votingRepository;

	private static DatabaseClient databaseClient;
	private static DatabaseUtil dUtil = new DatabaseUtil();
	private static Marshaller m;
	public static JAXBContext context;
	static XMLDocumentManager xmlMenager;
	static Unmarshaller unmarshaller;
	String XML_FILE = "data/";

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

	/**
	 * Add new act
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveAct(@RequestBody Dokument doc, Principal principal)
			throws JAXBException, IOException, SAXException {

		User user = userService.findByUsername(principal.getName());

		if (user == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "skupstina.xsd"));

		doc.setKorisnik(user.getUsername());

		m.setSchema(schema);
		m.setEventHandler(new MyValidationEventHandler());

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		File f = new File(XML_FILE + "/act.xml");
		FileOutputStream out = new FileOutputStream(f);
		m.marshal(doc, out);

		xmlMenager = databaseClient.newXMLDocumentManager();
		DocumentUriTemplate template = xmlMenager.newDocumentUriTemplate("xml");
		template.setDirectory("/acts/decisions/");

		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE + "act.xml"));
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add("/parliament/acts/proposed");

		DocumentDescriptor desc = xmlMenager.create(template, metadata, handle);

		Published published = new Published();

		published.setXmlLink(desc.getUri());
		published.setAccepted(false);
		published.setUser(user);

		publishedRepository.save(published);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/*
	 * Potrebno je proslediti jedan od dva parametra 1. proposed 2. accepted
	 */
	@RequestMapping(value = "/collection/{coll}", method = RequestMethod.GET)
	public ResponseEntity<List<SearchDTO>> findByCollection(@PathVariable String coll)
			throws JAXBException, IOException, SAXException {

		List<SearchDTO> searchDTO = new ArrayList<SearchDTO>();

		String collId = "/parliament/acts/" + coll;
		QueryManager queryManager = databaseClient.newQueryManager();
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();

		queryDefinition.setCollections(collId);

		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
		MatchDocumentSummary matches[] = results.getMatchResults();
		MatchDocumentSummary result;

		for (int i = 0; i < matches.length; i++) {
			result = matches[i];
			String link = result.getUri();
			String name[] = link.split("/");
			String broj = name[3];
			String title = getDocumentTitle(result.getUri());
			searchDTO.add(new SearchDTO(broj, title));
		}

		return new ResponseEntity<List<SearchDTO>>(searchDTO, HttpStatus.OK);
	}


	public String getDocumentTitle(String docId) throws JAXBException {

		String title = "";

		Dokument dokument = null;
		DOMHandle content = new DOMHandle();
		xmlMenager.read(docId, content);
		Document docc = content.get();
		dokument = (Dokument) unmarshaller.unmarshal(docc);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		title = dokument.getNaslov();



		return title;
	}
