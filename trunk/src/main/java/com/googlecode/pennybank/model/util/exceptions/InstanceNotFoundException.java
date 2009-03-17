package com.googlecode.pennybank.model.util.exceptions;

/**
 * An exception notifying that an instance was not found
 *
 * @author spenap
 */
@SuppressWarnings("serial")
public class InstanceNotFoundException extends InstanceException {

    /**
     * Creates a new exception with the specified arguments
     *
     * @param key The identifier being searched
     * @param className The entity class
     */
    public InstanceNotFoundException(Object key, String className) {
		super("Instance not found", key, className);
	}

}
