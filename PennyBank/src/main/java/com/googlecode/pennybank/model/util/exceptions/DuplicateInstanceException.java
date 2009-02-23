package com.googlecode.pennybank.model.util.exceptions;

/**
 * An exception which notifies the existence of a duplicated exception
 *
 * @author spenap
 */
@SuppressWarnings("serial")
public class DuplicateInstanceException extends InstanceException {

    /**
     * Creates a new exception with the specified arguments
     *
     * @param key The identifier of the instance which was being searched
     * @param className The entity type being searched
     */
    public DuplicateInstanceException(Object key, String className) {
        super("Duplicate instance", key, className);
    }
    
}
