package com.googlecode.pennybank.util.importation.exceptions;

/**
 * Exception raised when the file to import is accessed before being parsed
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class NotYetParsedException extends ImportationException {

	/**
	 * Creates a new exception
	 */
	public NotYetParsedException(String filename) {
		super(filename + " not yet parsed");
	}
}
