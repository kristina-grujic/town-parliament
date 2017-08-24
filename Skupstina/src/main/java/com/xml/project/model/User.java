package com.xml.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;

	private String lName;

	private String fName;

	@Column(unique = true)
	private String username;

	@Column(unique = true)
	private String email;

	private String pass;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private User_Role user_role;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Voting> voting = new HashSet<Voting>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Published> published = new HashSet<Published>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public User_Role getUser_role() {
		return user_role;
	}

	public void setUser_role(User_Role user_role) {
		this.user_role = user_role;
	}

	public Set<Voting> getVoting() {
		return voting;
	}

	public void setVoting(Set<Voting> voting) {
		this.voting = voting;
	}

	public Set<Published> getPublished() {
		return published;
	}

	public void setPublished(Set<Published> published) {
		this.published = published;
	}

}
