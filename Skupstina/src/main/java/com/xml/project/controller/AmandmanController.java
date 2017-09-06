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
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.xml.project.dto.MesagesDTO;
import com.xml.project.dto.SearchDTO;
import com.xml.project.jaxb.Amandman;
import com.xml.project.model.Published;
import com.xml.project.model.User;
import com.xml.project.repository.PublishedRepository;
import com.xml.project.service.UserService;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "api/amandman")
public class AmandmanController {

	@Autowired
	UserService userService;

	@Autowired
	PublishedRepository publishedRepository;

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
	 * Add new amandman
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MesagesDTO> saveAmandman(@RequestBody Amandman amandman, Principal principal)
			throws JAXBException, IOException, SAXException {

		MesagesDTO mesagesDTO = new MesagesDTO();
		User user = userService.findByUsername(principal.getName());

		if (user == null)
			return new ResponseEntity<MesagesDTO>(HttpStatus.BAD_REQUEST);

		amandman.setKorisnik(user.getUsername());
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "amandman.xsd"));

		m.setSchema(schema);
		m.setEventHandler(new MyValidationEventHandler());

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		File f = new File(XML_FILE + "/amandman.xml");
		FileOutputStream out = new FileOutputStream(f);
		m.marshal(amandman, out);

		xmlMenager = databaseClient.newXMLDocumentManager();
		DocumentUriTemplate template = xmlMenager.newDocumentUriTemplate("xml");

		template.setDirectory("/amandman/decisions/");

		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(XML_FILE + "amandman.xml"));
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add("/parliament/amandman/proposed");

		DocumentDescriptor desc = xmlMenager.create(template, metadata, handle);


		Published published = new Published();

		published.setXmlLink(desc.getUri());
		published.setAccepted(false);
		published.setUser(user);

		publishedRepository.save(published);
		mesagesDTO.setMessage("uspesno");
		return new ResponseEntity<MesagesDTO>(mesagesDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/collection/{coll}", method = RequestMethod.GET)
	public ResponseEntity<List<SearchDTO>> findByCollection(@PathVariable String coll)
			throws JAXBException, IOException, SAXException {

		List<SearchDTO> searchDTO = new ArrayList<SearchDTO>();

		String collId = "/parliament/amandman/" + coll;
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
	public ResponseEntity<MesagesDTO> deleteCollection(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {

		String collId = "/amandman/decisions/" + docId + ".xml";
		MesagesDTO mesagesDTO = new MesagesDTO();

		GenericDocumentManager docMgr = databaseClient.newDocumentManager();

		docMgr.delete(collId);


		mesagesDTO.setMessage("delete");
		return new ResponseEntity<>(mesagesDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/find/{docId}", method = RequestMethod.GET)
	public ResponseEntity<Amandman> findByIdAct(@PathVariable String docId)
			throws JAXBException, IOException, SAXException {

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

		return new ResponseEntity<Amandman>(amandman, HttpStatus.OK);
	}

	@RequestMapping(value = "/convert/{docId}/{typeId}", method = RequestMethod.GET)
	public void convert(HttpServletResponse response, @PathVariable String docId, @PathVariable String typeId)
			throws JAXBException, IOException, SAXException, DocumentException, TransformerException {

		String doc = "/amandman/decisions/" + docId + ".xml";
		Amandman amandman = null;
		DOMHandle content = new DOMHandle();
		xmlMenager.read(doc, content);
		Document docc = content.get();

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "amandman.xsd"));

		unmarshaller.setSchema(schema);
		amandman = (Amandman) unmarshaller.unmarshal(docc);
		String outputFileName = "data/" + docId + ".html";
		OutputStream htmlFile = new FileOutputStream(outputFileName);

		TransformerFactory tf = TransformerFactory.newInstance();
		StreamSource xslt = new StreamSource("data/amandman.xsl");
		Transformer transformer = tf.newTransformer(xslt);
		JAXBContext jc = JAXBContext.newInstance(Amandman.class);
		JAXBSource source = new JAXBSource(jc, amandman);
		transformer.transform(source, new StreamResult(htmlFile));

		File file1 = new File(XML_FILE  +docId + ".html");
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


		String doc = "/amandman/decisions/" + docId + ".xml";
		Amandman amandman = null;

		DOMHandle content = new DOMHandle();
		xmlMenager.read(doc, content);

		Document docc = content.get();

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(XML_FILE + "amandman.xsd"));

		unmarshaller.setSchema(schema);
		amandman = (Amandman) unmarshaller.unmarshal(docc);

		String outputFileName = "data/" + docId + ".html";
		OutputStream htmlFile = new FileOutputStream(outputFileName);

		TransformerFactory tf = TransformerFactory.newInstance();
		StreamSource xslt = new StreamSource("data/pdfAmandman.xsl");
		Transformer transformer = tf.newTransformer(xslt);

		JAXBContext jc = JAXBContext.newInstance(Amandman.class);
		JAXBSource source = new JAXBSource(jc, amandman);

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


	@RequestMapping(value = "/accept/{docId}", method = RequestMethod.GET)
	public ResponseEntity<MesagesDTO> accepted(@PathVariable String docId) throws SAXException, JAXBException, IOException{


		MesagesDTO mesagesDTO = new MesagesDTO();

		AcceptAmandman.Accept(docId);

		mesagesDTO.setMessage("prihvaceno");
		return new ResponseEntity<MesagesDTO>(mesagesDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/decline/{docId}", method = RequestMethod.GET)
	public ResponseEntity<MesagesDTO> declined(@PathVariable String docId) throws SAXException, JAXBException, IOException{


		MesagesDTO mesagesDTO = new MesagesDTO();

		AcceptAmandman.Accept(docId);

		mesagesDTO.setMessage("prihvaceno");
		return new ResponseEntity<MesagesDTO>(mesagesDTO, HttpStatus.OK);
	}





	public String getDocumentTitle(String docId) throws JAXBException {

		String title = "";

		Amandman amandman = null;
		DOMHandle content = new DOMHandle();
		xmlMenager.read(docId, content);
		Document docc = content.get();
		amandman = (Amandman) unmarshaller.unmarshal(docc);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		title = amandman.getNaslovAkta();

		return title;
	}
}
