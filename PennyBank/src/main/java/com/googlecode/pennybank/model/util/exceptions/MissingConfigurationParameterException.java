package com.googlecode.pennybank.model.util.exceptions;

/**
 * Exception notifying that a configuration parameter was not found
 *
 * @author spenap
 */
@SuppressWarnings("serial")
public class MissingConfigurationParameterException extends Exception {

    /**
     * Creates a new exception with the specified arguments
     * 
     * @param name The parameter name which was not found
     */
    public MissingConfigurationParameterException(String name) {
        super("Missing configuration parameter:" + name);
    }
}
