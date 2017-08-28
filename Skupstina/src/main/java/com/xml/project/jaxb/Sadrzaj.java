package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"broj_clana", 
		"naziv_propisa", 
		"id",
		"korisnik" 
})
@XmlRootElement(name = "sadrzaj")
public class Sadrzaj {

	@XmlElement(name = "broj_clana")
	private int broj_clana;
	@XmlElement(name = "naziv_propisa")
	private String naziv_propisa;
	@XmlAttribute(name = "id", namespace = "http://www.parlament.gov.rs/amandmani")
	private String id;
	@XmlAttribute(name = "Korisnik", namespace = "http://www.parlament.gov.rs/amandmani")
	private String korisnik;

	public int getBroj_clana() {
		return broj_clana;
	}

	public void setBroj_clana(int broj_clana) {
		this.broj_clana = broj_clana;
	}

	public String getNaziv_propisa() {
		return naziv_propisa;
	}

	public void setNaziv_propisa(String naziv_propisa) {
		this.naziv_propisa = naziv_propisa;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}

}
