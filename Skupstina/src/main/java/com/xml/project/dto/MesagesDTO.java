package com.xml.project.dto;

public class MesagesDTO {

	private Long id;
	private String jwt;
	private String error;
	private String message;
	private String rola;
	private boolean vote;

	public MesagesDTO() {
		super();
	}

	public MesagesDTO(Long id, String jwt, String error, String message,
			String rola, boolean vote) {
		super();
		this.id = id;
		this.jwt = jwt;
		this.error = error;
		this.message = message;
		this.rola = rola;
		this.vote = vote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRola() {
		return rola;
	}

	public void setRola(String rola) {
		this.rola = rola;
	}

	public boolean isVote() {
		return vote;
	}

	public void setVote(boolean vote) {
		this.vote = vote;
	}

}
