package com.xml.project.dto;

import java.util.HashSet;
import java.util.Set;

import com.xml.project.model.Published;
import com.xml.project.model.Voting;

public class PublishedDTO {

	private Long id;
	private String xmlLink;
	private String type;
	private boolean accepted;
	private UserDTO userDTO;
	private Set<VoteDTO> voteDTO;

	public PublishedDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PublishedDTO(Long id, String xmlLink, String type, boolean accepted,
			UserDTO userDTO, Set<VoteDTO> voteDTO) {
		super();
		this.id = id;
		this.xmlLink = xmlLink;
		this.type = type;
		this.accepted = accepted;
		this.userDTO = userDTO;
		this.voteDTO = voteDTO;
	}

	public PublishedDTO(Published pub) {
		this.id = pub.getId();
		this.xmlLink = pub.getXmlLink();
		this.type = pub.getType();
		this.accepted = pub.isAccepted();
		if (pub.getUser() != null)
			this.userDTO = new UserDTO(pub.getUser());
		
		this.voteDTO = new HashSet<VoteDTO>();
		for(Voting v: pub.getVoting()) {
			
			this.voteDTO.add(new VoteDTO(v));
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXmlLink() {
		return xmlLink;
	}

	public void setXmlLink(String xmlLink) {
		this.xmlLink = xmlLink;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public Set<VoteDTO> getVoteDTO() {
		return voteDTO;
	}

	public void setVoteDTO(Set<VoteDTO> voteDTO) {
		this.voteDTO = voteDTO;
	}

}
