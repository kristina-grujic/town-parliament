package com.xml.project.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"naslov",
		"sluzbeniList", 
		"propisi", 
		"sadrzaj",
		"id",
		"korisnik",
		"odobreno"
		
})
@XmlRootElement(name = "dokument")
public class Dokument {

	@XmlElement(name = "naslov")
	private String naslov;
	@XmlElement(name = "sluzbeniList")
	private SluzbeniList sluzbeniList;
	@XmlElement(name = "propisi")
	private List<Propisi> propisi;
	@XmlElement(name = "sadrzaj")
	private Sadrzaj sadrzaj;
	@XmlAttribute(name = "id", namespace = "http://www.parlament.gov.rs/amandmani")
	private String id;
	@XmlAttribute(name = "Korisnik", namespace = "http://www.parlament.gov.rs/amandmani")
	private String korisnik;
	@XmlAttribute(name = "odobreno", namespace = "http://www.parlament.gov.rs/amandmani")
	private boolean odobreno;

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public SluzbeniList getSluzbeniList() {
		return sluzbeniList;
	}

	public void setSluzbeniList(SluzbeniList sluzbeniList) {
		this.sluzbeniList = sluzbeniList;
	}

	public List<Propisi> getPropisi() {
		if(propisi == null){
			propisi = new ArrayList<Propisi>();
		}
		return this.propisi;
	}

	public void setPropisi(List<Propisi> propisi) {
		this.propisi = propisi;
	}

	public Sadrzaj getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(Sadrzaj sadrzaj) {
		this.sadrzaj = sadrzaj;
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

	public boolean isOdobreno() {
		return odobreno;
	}

	public void setOdobreno(boolean odobreno) {
		this.odobreno = odobreno;
	}

}
