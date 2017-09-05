package com.xml.project.dto;

public class SearchDTO {

	private String docUrl;
	private String docTitle;
	//private String amanTitle;

	public SearchDTO(String docUrl, String docTitle) {
		super();
		this.docUrl = docUrl;
		this.docTitle = docTitle;
		//this.amanTitle = amanTitle;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	/*
	public String getAmanTitle() {
		return amanTitle;
	}

	public void setAmanTitle(String amanTitle) {
		this.amanTitle = amanTitle;
	}
	*/
}
