package com.googlecode.pennybank.util.importation.exceptions;

@SuppressWarnings("serial")
public class ParseException extends ImportationException {

	public ParseException(String filename) {

		super(filename);
		// TODO Auto-generated constructor stub
	}

	public ParseException(Exception e) {

		super(e);
	}

}
