package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"broj_lista", 
		"cena", 
		"mestoDatum" 
})
@XmlRootElement(name = "sluzbeniList")
public class SluzbeniList {

	@XmlElement(name = "broj_lista")
	private String broj_lista;
	@XmlElement(name = "cena")
	private float cena;
	@XmlElement(name = "mestoDatum")
	private MestoDatum mestoDatum;

	public String getBroj_lista() {
		return broj_lista;
	}

	public void setBroj_lista(String broj_lista) {
		this.broj_lista = broj_lista;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}

	public MestoDatum getMestoDatum() {
		return mestoDatum;
	}

	public void setMestoDatum(MestoDatum mestoDatum) {
		this.mestoDatum = mestoDatum;
	}

}
