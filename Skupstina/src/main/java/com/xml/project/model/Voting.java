package com.xml.project.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Voting {

	@Id
	@GeneratedValue
	private Long id;
	private String tip;
	private String name;
	private boolean yn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Published published;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isYn() {
		return yn;
	}

	public void setYn(boolean yn) {
		this.yn = yn;
	}

	public Published getPublished() {
		return published;
	}

	public void setPublished(Published published) {
		this.published = published;
	}

}
