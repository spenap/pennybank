package com.googlecode.pennybank.model.util.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * An exception notifying of an internal, unexpected error
 *
 * @author spenap
 */
@SuppressWarnings("serial")
public class InternalErrorException extends Exception {

	private Exception encapsulatedException;

    /**
     * Creates a new exception with the specified arguments
     *
     * @param exception The exception causing the InternalException
     */
    public InternalErrorException(Exception exception) {
		encapsulatedException = exception;
	}

    /**
     *
     * @return the message in the encapsulated exception
     */
    @Override
	public String getMessage() {
		return encapsulatedException.getMessage();
	}

    /**
     *
     * @return The internal exception
     */
    public Exception getEncapsulatedException() {
		return encapsulatedException;
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
		encapsulatedException.printStackTrace(printStream);
	}

    /**
     * Prints the stack trace with the given print writer
     * @param printWriter The writer to print the stack with
     */
    @Override
	public void printStackTrace(PrintWriter printWriter) {
		super.printStackTrace(printWriter);
		printWriter.println("***Information about encapsulated exception***");
		encapsulatedException.printStackTrace(printWriter);
	}

}
