package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "podaciGlave",
    "podnaslovGlave",
    "clan"
})
@XmlRootElement(name = "glava")
public class Glava {

	@XmlElement(name = "podaciGlave")
	private PodaciGlave podaciGlave;
	@XmlElement(name = "podnaslovGlave")
	private String podnaslovGlave;
	@XmlElement(name = "clan")
	private Clan[] clan;
	@XmlAttribute(name = "id", namespace = "http://www.parlament.gov.rs/amandmani")
	private String id;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public PodaciGlave getPodaciGlave() {
		return podaciGlave;
	}


	public void setPodaciGlave(PodaciGlave podaciGlave) {
		this.podaciGlave = podaciGlave;
	}


	public String getPodnaslovGlave() {
		return podnaslovGlave;
	}


	public void setPodnaslovGlave(String podnaslovGlave) {
		this.podnaslovGlave = podnaslovGlave;
	}


	public Clan[] getClan() {
		return clan;
	}


	public void setClan(Clan[] clan) {
		this.clan = clan;
	}


	
}
