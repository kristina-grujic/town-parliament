package com.xml.project.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"glava",
		"potpis_presednika"
})
@XmlRootElement(name = "zavrsniDeo")
public class ZavrsniDeo {

	@XmlElement(name = "glava", required = true)
	private List<Glava> glava;
	@XmlElement(name = "potpis_presednika")
	private String potpis_presednika;

	public ZavrsniDeo() {
	}

	public List<Glava> getGlava() {
		return glava;
	}

	public void setGlava(List<Glava> glava) {
		this.glava = glava;
	}

	public String getPotpis_presednika() {
		return potpis_presednika;
	}

	public void setPotpis_presednika(String potpis_presednika) {
		this.potpis_presednika = potpis_presednika;
	}
}
