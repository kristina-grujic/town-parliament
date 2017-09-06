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
		"dopunaIzmena",
		"glava"
})
@XmlRootElement(name = "dopunaZakonaAmandmana")
public class DopunaZakonaAmandamana {
	
	@XmlAttribute(name = "dopunaIzmena", namespace = "http://www.parlament.gov.rs/amandmani")
	private boolean dopunaIzmena;
	
	@XmlElement(name = "glava")
	private List<Glava> glava;

	public boolean isDopunaIzmena() {
		return dopunaIzmena;
	}

	public void setDopunaIzmena(boolean dopunaIzmena) {
		this.dopunaIzmena = dopunaIzmena;
	}

	public List<Glava> getGlava() {
		return glava;
	}

	public void setGlava(List<Glava> glava) {
		this.glava = glava;
	}
	
	
	
}
