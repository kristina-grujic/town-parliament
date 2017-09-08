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

	@RequestMapping(value = "/accept/{docId}", method = RequestMethod.GET)
	public ResponseEntity<MesagesDTO> acceptAct(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {

		MesagesDTO mesagesDTO = new MesagesDTO();
		String doc = "/acts/decisions/" + docId + ".xml";
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		xmlMenager.readMetadata(doc, metadata);

		DocumentCollections collections = metadata.getCollections();
		collections.remove("/parliament/acts/proposed");
		collections.add("/parliament/acts/accepted");
		xmlMenager.writeMetadata(doc, metadata);
		mesagesDTO.setVote(true);
		return new ResponseEntity<MesagesDTO>(mesagesDTO, HttpStatus.OK);
	}

	/**
	 * Search for name file (xxx.xml)
	 *
	 * @param docId
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 * @throws SAXException
	 */
	@RequestMapping(value = "/find/{docId}", method = RequestMethod.GET)
	public ResponseEntity<Dokument> findByIdAct(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {

		Dokument dokument = null;

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "skupstina.xsd"));

		unmarshaller.setSchema(schema);

		DOMHandle content = new DOMHandle();

		String doc = "/acts/decisions/" + docId + ".xml";

		xmlMenager.read(doc, content);

		Document docc = content.get();
		unmarshaller.setEventHandler(new MyValidationEventHandler());
		dokument = (Dokument) unmarshaller.unmarshal(docc);

		return new ResponseEntity<Dokument>(dokument, HttpStatus.OK);
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

	@RequestMapping(value = "/delete/{docId}", method = RequestMethod.DELETE)
	public static ResponseEntity<String> deleteCollection(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {

		GenericDocumentManager docMgr = databaseClient.newDocumentManager();
		String collId = "/acts/decisions/" + docId + ".xml";
		docMgr.delete(collId);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Ispis svih galsova za presednika na osnovu kog on odlucuje da li se akt usvaja ili ne usvaja
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/adopt/proposal/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<VoteDTO>> getAdopt(Principal principal, @PathVariable String id) {


		List<Voting> Voting = votingRepository.findByName(id);

		List<VoteDTO> voteDTO = new ArrayList<>();
		for(Voting v : Voting) {


			voteDTO.add(new VoteDTO(v));
		}

		return new ResponseEntity<>(voteDTO, HttpStatus.OK);
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

	@RequestMapping(value = "/convert/{docId}/{typeId}", method = RequestMethod.GET)
	public void convert(HttpServletResponse response, @PathVariable String docId, @PathVariable String typeId)
			throws JAXBException, IOException, SAXException, DocumentException, TransformerException {

		String doc = "/acts/decisions/" + docId + ".xml";
		Dokument dokument = null;

		DOMHandle content = new DOMHandle();
		xmlMenager.read(doc, content);

		Document docc = content.get();

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "skupstina.xsd"));

		unmarshaller.setSchema(schema);
		dokument = (Dokument) unmarshaller.unmarshal(docc);

		String outputFileName = "data/" + docId + ".html";
		OutputStream htmlFile = new FileOutputStream(outputFileName);

		TransformerFactory tf = TransformerFactory.newInstance();
		StreamSource xslt = new StreamSource("data/act.xsl");
		Transformer transformer = tf.newTransformer(xslt);

		JAXBContext jc = JAXBContext.newInstance(Dokument.class);
		JAXBSource source = new JAXBSource(jc, dokument);

		transformer.transform(source, new StreamResult(htmlFile));

		File file1 = new File(XML_FILE + docId + ".html");
		String mimeType = URLConnection.guessContentTypeFromName(file1.getName());

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file1.getName() + "\""));
		response.setContentLength((int) file1.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file1));
		FileCopyUtils.copy(inputStream, response.getOutputStream());

		file1.delete();
	}

	@RequestMapping(value = "/download/pdf/{docId}", method = RequestMethod.GET)
	public void downloadPDF(HttpServletResponse response, @PathVariable String docId)
			throws JAXBException, IOException, SAXException, DocumentException, TransformerException {

		String doc = "/acts/decisions/" + docId + ".xml";
		Dokument dokument = null;

		DOMHandle content = new DOMHandle();
		xmlMenager.read(doc, content);

		Document docc = content.get();

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "skupstina.xsd"));

		unmarshaller.setSchema(schema);
		dokument = (Dokument) unmarshaller.unmarshal(docc);

		String outputFileName = "data/" + docId + ".html";
		OutputStream htmlFile = new FileOutputStream(outputFileName);

		TransformerFactory tf = TransformerFactory.newInstance();
		StreamSource xslt = new StreamSource("data/pdfAct.xsl");
		Transformer transformer = tf.newTransformer(xslt);

		JAXBContext jc = JAXBContext.newInstance(Dokument.class);
		JAXBSource source = new JAXBSource(jc, dokument);

		transformer.transform(source, new StreamResult(htmlFile));

		// PDF FILE
		com.itextpdf.text.Document myDoc = new com.itextpdf.text.Document();
		PdfWriter writer = PdfWriter.getInstance(myDoc, new FileOutputStream(XML_FILE + docId + ".pdf"));

		myDoc.open();

		XMLWorkerHelper.getInstance().parseXHtml(writer, myDoc, new FileInputStream(XML_FILE + docId + ".html"));
		myDoc.close();

		File file1 = new File(XML_FILE + docId + ".pdf");
		String mimeType = URLConnection.guessContentTypeFromName(file1.getName());
		// END OF PDF FILE

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file1.getName() + "\""));
		response.setContentLength((int) file1.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file1));
		FileCopyUtils.copy(inputStream, response.getOutputStream());

		// delete pdf and html file
		file1.delete();
		file1 = new File(XML_FILE + docId + ".html");
		file1.delete();

	}

	@RequestMapping(value="download/rdf/{docId}", method=RequestMethod.GET)
	public void downloadRDF(HttpServletResponse response, @PathVariable String docId) throws JAXBException, SAXException, TransformerException, IOException
	{
		Helper helper = new Helper();
		helper.generateRDF(docId);

		//download rdf file
		File rdfFile = new File(XML_FILE + docId + ".rdf");
		String mimeType = URLConnection.guessContentTypeFromName(rdfFile.getName());

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + rdfFile.getName() + "\""));
		response.setContentLength((int) rdfFile.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(rdfFile));
		FileCopyUtils.copy(inputStream, response.getOutputStream());

		//delete file
		rdfFile.delete();

	}
}
