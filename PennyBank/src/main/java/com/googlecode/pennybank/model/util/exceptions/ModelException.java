package com.googlecode.pennybank.model.util.exceptions;

/**
 * Exception notifying a model-related exception
 *
 * @author spenap
 */
@SuppressWarnings("serial")
public class ModelException extends Exception {

    /**
     * Creates a new exception with the specified arguments
     * 
     * @param string A message with the description
     */
    public ModelException(String string) {
		super(string);
	}

}
