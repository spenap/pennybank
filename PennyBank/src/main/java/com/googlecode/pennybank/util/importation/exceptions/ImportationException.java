package com.googlecode.pennybank.util.importation.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Exception notifying of an importation-related exception
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class ImportationException extends Exception {

	private Exception innerException;

    /**
     * Creates a new exception with the specified arguments
     *
     * @param filename The file being imported
     */
    protected ImportationException(String filename) {

		// TODO Create more explicit exceptions
		super(filename);
	}

    /**
     * Creates a new exception with the specified arguments
     * 
     * @param e The inner exception
     */
    protected ImportationException(Exception e) {

		super(e);
		this.innerException = e;

	}

    /**
     *
     * @return The message in the inner exception
     */
    @Override
	public String getMessage() {

		return innerException.getMessage();
	}

    /**
     *
     * @return the encapsulated exception
     */
    public Exception getEncapsulatedException() {

		return innerException;
	}

    /**
     * Prints the stack trace
     */
    @Override
	public void printStackTrace() {

		printStackTrace(System.err);
	}

    /**
     * Prints the stack trace in a given print stream
     * @param printStream The stream to print the stack trace in
     */
    @Override
	public void printStackTrace(PrintStream printStream) {

		super.printStackTrace(printStream);
		printStream.println("***Information about encapsulated exception***");
		innerException.printStackTrace(printStream);
	}

    /**
     * Prints the stack trace with a given print writer
     * @param printWriter The print writer to print the stack with
     */
    @Override
	public void printStackTrace(PrintWriter printWriter) {

		super.printStackTrace(printWriter);
		printWriter.println("***Information about encapsulated exception***");
		innerException.printStackTrace(printWriter);
	}
}
