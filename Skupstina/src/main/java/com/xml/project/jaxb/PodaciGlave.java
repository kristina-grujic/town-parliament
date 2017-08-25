package com.xml.project.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "naslovGlave",
    "broj_glave"
})
@XmlRootElement(name = "podaciGlave")
public class PodaciGlave {

	@XmlElement(name = "naslovGlave")
    private String naslovGlave;
    @XmlElement(name = "broj_glave")
    private String broj_glave;
    
	public String getNaslovGlave() {
		return naslovGlave;
	}
	public void setNaslovGlave(String naslovGlave) {
		this.naslovGlave = naslovGlave;
	}
	public String getBroj_glave() {
		return broj_glave;
	}
	public void setBroj_glave(String broj_glave) {
		this.broj_glave = broj_glave;
	}
    
        

}
