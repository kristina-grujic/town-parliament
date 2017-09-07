package com.xml.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Published {

	@Id
	@GeneratedValue
	private Long id;
	private String xmlLink;
	private String type;
	private boolean accepted;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REFRESH)
	private Set<Voting> voting = new HashSet<Voting>();

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Voting> getVoting() {
		return voting;
	}

	public void setVoting(Set<Voting> voting) {
		this.voting = voting;
	}


}
