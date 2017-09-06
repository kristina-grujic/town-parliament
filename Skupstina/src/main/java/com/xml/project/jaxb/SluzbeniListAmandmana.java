package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"brojListaAkta",
		"idAkta",
		"mestoDatum"
})
@XmlRootElement(name = "sluzbeniListAmandmana")
public class SluzbeniListAmandmana {

	@XmlElement(name = "brojListaAkta")
	private String brojListaAkta;
	
	@XmlElement(name = "idAkta")
	private String idAkta;
	
	@XmlElement(name = "mestoDatum")
	private MestoDatum mestoDatum;

	public String getBrojListaAkta() {
		return brojListaAkta;
	}

	public void setBrojListaAkta(String brojListaAkta) {
		this.brojListaAkta = brojListaAkta;
	}

	public String getIdAkta() {
		return idAkta;
	}

	public void setIdAkta(String idAkta) {
		this.idAkta = idAkta;
	}

	public MestoDatum getMestoDatum() {
		return mestoDatum;
	}

	public void setMestoDatum(MestoDatum mestoDatum) {
		this.mestoDatum = mestoDatum;
	}

	
}
