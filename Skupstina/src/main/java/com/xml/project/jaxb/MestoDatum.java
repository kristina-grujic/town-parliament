package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"mesto", 
		"datum" 
})
@XmlRootElement(name = "mestoDatum")
public class MestoDatum {

	@XmlElement(name = "mesto")
	private String mesto;
	@XmlElement(name = "datum")
	private String datum;
	
	public String getMesto() {
		return mesto;
	}
	
	public void setMesto(String mesto) {
		this.mesto = mesto;
	}
	
	public String getDatum() {
		return datum;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	
}
