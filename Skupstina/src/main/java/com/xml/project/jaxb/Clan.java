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
    "podaciClana",
    "opis"
})
@XmlRootElement(name = "clan")
public class Clan {

	
	@XmlAttribute(name = "id", namespace = "http://www.parlament.gov.rs/amandmani")
	private String id;
	@XmlElement(name = "podaciClana")
	private Clan.PodaciClana podaciClana;
	@XmlElement(name = "opis")
	private String opis;
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Clan.PodaciClana getPodaciClana() {
		return podaciClana;
	}



	public void setPodaciClana(Clan.PodaciClana podaciClana) {
		this.podaciClana = podaciClana;
	}



	public String getOpis() {
		return opis;
	}



	public void setOpis(String opis) {
		this.opis = opis;
	}



	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	    "naslovClana",
	    "brojClana"
	})
	public static class PodaciClana {
		
		@XmlElement(name = "naslovClana")
		protected String naslovClana;
		@XmlElement(name = "brojClana")
		protected String brojClana;
		
		public String getNaslovClana() {
			return naslovClana;
		}
		public void setNaslovClana(String naslovClana) {
			this.naslovClana = naslovClana;
		}
		public String getBrojClana() {
			return brojClana;
		}
		public void setBrojClana(String brojClana) {
			this.brojClana = brojClana;
		}
		
		
		
	}
}
