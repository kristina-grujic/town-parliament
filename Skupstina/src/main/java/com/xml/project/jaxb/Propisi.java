package com.xml.project.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"preambula", 
		"naziv_propisa", 
		"uvodniDeo",
		"glavniDeo", 
		"zavrsniDeo", 
		"dopunaZakona", 
		"id", 
		"korisnik" 
})
@XmlRootElement(name = "propisi")
public class Propisi {

	@XmlElement(name = "preambula")
	private String preambula;

	@XmlElement(name = "naziv_propisa")
	private String naziv_propisa;

	@XmlElement(name = "uvodniDeo")
	private List<UvodniDeo> uvodniDeo;

	@XmlElement(name = "glavniDeo")
	private List<GlavniDeo> glavniDeo;

	@XmlElement(name = "zavrsniDeo")
	private List<ZavrsniDeo> zavrsniDeo;

	@XmlElement(name = "dopunaZakona")
	private DopunaZakona dopunaZakona;

	@XmlAttribute(name = "id", namespace = "http://www.parlament.gov.rs/amandmani")
	private String id;

	@XmlAttribute(name = "Korisnik", namespace = "http://www.parlament.gov.rs/amandmani")
	private String korisnik;

	public String getPreambula() {
		return preambula;
	}

	public void setPreambula(String preambula) {
		this.preambula = preambula;
	}

	public String getNaziv_propisa() {
		return naziv_propisa;
	}

	public void setNaziv_propisa(String naziv_propisa) {
		this.naziv_propisa = naziv_propisa;
	}

	public List<UvodniDeo> getUvodniDeo() {
		return uvodniDeo;
	}

	public void setUvodniDeo(List<UvodniDeo> uvodniDeo) {
		this.uvodniDeo = uvodniDeo;
	}

	public List<GlavniDeo> getGlavniDeo() {
		return glavniDeo;
	}

	public void setGlavniDeo(List<GlavniDeo> glavniDeo) {
		this.glavniDeo = glavniDeo;
	}

	public List<ZavrsniDeo> getZavrsniDeo() {
		return zavrsniDeo;
	}

	public void setZavrsniDeo(List<ZavrsniDeo> zavrsniDeo) {
		this.zavrsniDeo = zavrsniDeo;
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

	public DopunaZakona getDopunaZakona() {
		return dopunaZakona;
	}

	public void setDopunaZakona(DopunaZakona dopunaZakona) {
		this.dopunaZakona = dopunaZakona;
	}

}
