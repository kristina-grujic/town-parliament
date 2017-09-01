package com.xml.project.controller;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 * Implementira metodu koja handluje greške nastale prilikom validacije u odnosu
 * na XML schemu tokom unmarshallovanja.
 *
 */
public class MyValidationEventHandler implements ValidationEventHandler {

	public boolean handleEvent(ValidationEvent event) {

		// Ako nije u pitanju WARNING metoda vraća false
		if (event.getSeverity() != ValidationEvent.WARNING) {
			ValidationEventLocator validationEventLocator = event.getLocator();
			// Dalje izvršavanje se prekida
			return false;
		} else {
			ValidationEventLocator validationEventLocator = event.getLocator();
			// Nastavlja se dalje izvršavanje
			return true;
		}
	}

}
