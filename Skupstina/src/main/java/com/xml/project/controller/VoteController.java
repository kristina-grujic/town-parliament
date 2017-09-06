package com.xml.project.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.xml.project.dto.MesagesDTO;
import com.xml.project.dto.VoteDTO;
import com.xml.project.model.Published;
import com.xml.project.model.User;
import com.xml.project.model.Voting;
import com.xml.project.repository.PublishedRepository;
import com.xml.project.service.UserService;
import com.xml.project.service.VotingService;

@RestController
@RequestMapping(value = "api/voting")
public class VoteController {

	@Autowired
	UserService userService;

	@Autowired
	VotingService votingService;

	@Autowired
	PublishedRepository publishedRepository;

	@Autowired
	ActController actControler;

	AcceptAmandman acceptAmandman;

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MesagesDTO> vote(@RequestBody VoteDTO dto, Principal principal) throws JAXBException, IOException, SAXException {

		MesagesDTO messageDTO = new MesagesDTO();

		User user = userService.findByUsername(principal.getName());
		String xmlLink = "/acts/decisions/"+dto.getName()+".xml";
		Published published = publishedRepository.findByXmlLink(xmlLink);

		List<Voting> votes = votingService.findByName(dto.getName());
		for (Voting vot : votes) {
			if (vot.getUser() == user){
				messageDTO.setError("glasali");
				return new ResponseEntity<MesagesDTO>(messageDTO, HttpStatus.OK);
			}
		}

		Voting voting = new Voting();

		voting.setName(dto.getName());
		voting.setTip(dto.getTip());
		voting.setUser(user);
		voting.setYn(dto.isYn());
		voting.setPublished(published);

		votingService.save(voting);

		messageDTO.setMessage("glassali");

		messageDTO.setVote(false);
		return new ResponseEntity<MesagesDTO>(messageDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{docId}", method = RequestMethod.GET)
	public ResponseEntity<List<VoteDTO>> listVoit(Principal principal, @PathVariable String docId) {
		
		List<Voting> voting = votingService.findAll();

		List<VoteDTO> voteDTO = new ArrayList<>();
		for(Voting v: voting) {
			voteDTO.add(new VoteDTO(v));
		}

		return new ResponseEntity<List<VoteDTO>>(voteDTO, HttpStatus.OK);
	}
}
