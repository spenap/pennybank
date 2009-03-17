package com.googlecode.pennybank.util.importation.exceptions;

/**
 * Creates a new parse-related exception
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class ParseException extends ImportationException {

	/**
	 * Creates a new parse exception related to a given filename
	 * 
	 * @param filename
	 *            The filename being parsed
	 */
	public ParseException(String filename) {
		super("Error parsing " + filename);
	}

	/**
	 * Creates a new parse exception from another exception
	 * 
	 * @param e
	 *            The exception causing the parse exception
	 */
	public ParseException(Exception e) {
		super(e);
	}
}
