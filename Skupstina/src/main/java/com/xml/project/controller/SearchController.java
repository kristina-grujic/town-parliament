package com.xml.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.xml.project.dto.SearchDTO;
import com.xml.project.jaxb.Amandman;
import com.xml.project.jaxb.Dokument;
import com.xml.project.util.DatabaseUtil;

@RestController
@RequestMapping(value = "api/search")
public class SearchController {

	
	
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

	@RequestMapping(value = "/{act}/{type}/{text}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<SearchDTO>> search(@PathVariable String text, @PathVariable String type, @PathVariable String act) throws JAXBException{
		
		
		List<SearchDTO> searchDTO = new ArrayList<SearchDTO>();
		
		
		QueryManager queryManager = databaseClient.newQueryManager();
		
		String COLLECTION = "/parliament/"+act+"/"+type;
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		queryDefinition.setCriteria(text);
		queryDefinition.setCollections(COLLECTION);

		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());

		MatchDocumentSummary matches[] = results.getMatchResults();

		MatchDocumentSummary result;

		for(int i = 0; i < matches.length; i++){
			result = matches[i];

			String link = result.getUri();
			String name[] = link.split("/");
			String broj = name[3];
			if(act.equals("acts")){
				String title = getDocumentTitle(result.getUri());
				searchDTO.add(new SearchDTO(broj, title));
			} else {
				String title = getDocumentTitleAmandman(result.getUri());
				searchDTO.add(new SearchDTO(broj, title));
			}
			
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
	
	
public String getDocumentTitleAmandman(String docId) throws JAXBException {
		
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