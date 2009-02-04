package com.googlecode.pennybank.util.importation.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class ImportationException extends Exception {

	private Exception innerException;

	protected ImportationException(String filename) {

		// TODO Create more explicit exceptions
		super(filename);
	}

	protected ImportationException(Exception e) {

		super(e);
		this.innerException = e;

	}

	public String getMessage() {

		return innerException.getMessage();
	}

	public Exception getEncapsulatedException() {

		return innerException;
	}

	public void printStackTrace() {

		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream printStream) {

		super.printStackTrace(printStream);
		printStream.println("***Information about encapsulated exception***");
		innerException.printStackTrace(printStream);
	}

	public void printStackTrace(PrintWriter printWriter) {

		super.printStackTrace(printWriter);
		printWriter.println("***Information about encapsulated exception***");
		innerException.printStackTrace(printWriter);
	}
}
