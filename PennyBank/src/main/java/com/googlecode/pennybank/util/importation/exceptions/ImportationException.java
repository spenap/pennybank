package com.googlecode.pennybank.util.importation.exceptions;

/**
 * Exception notifying of an importation-related exception
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class ImportationException extends Exception {

	public ImportationException(String message) {
		super("Error during importation:\n" + message);
	}

	public ImportationException(Exception e) {
		super(e);
	}

}
