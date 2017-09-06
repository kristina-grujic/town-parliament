package com.xml.project.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"glava" 
})
@XmlRootElement(name = "dopunaZakona")
public class DopunaZakona {

	@XmlElement(name = "glava")
	private List<Glava> glava;

	public DopunaZakona() {
	}

	public List<Glava> getGlava() {
		return glava;
	}

	public void setGlava(List<Glava> glava) {
		this.glava = glava;
	}

}
