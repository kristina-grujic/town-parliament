package com.xml.project.dto;

public class VotePresidetnDTO {

	private Long id;
	private String tip;
	private String name;
	private boolean yn;
	private String xmlLink;
	private String type;

	public VotePresidetnDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VotePresidetnDTO(Long id, String tip, String name, boolean yn,
			String xmlLink, String type) {
		super();
		this.id = id;
		this.tip = tip;
		this.name = name;
		this.yn = yn;
		this.xmlLink = xmlLink;
		this.type = type;
	}

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

}
