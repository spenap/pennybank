package com.googlecode.pennybank.model.util.exceptions;

/**
 * An instance related exception
 *
 * @author spenap
 */
@SuppressWarnings("serial")
public class InstanceException extends ModelException {

	private Object key;
	private String className;

    /**
     * Creates a new exception with the specified arguments
     *
     * @param specificMessage The exception specific message
     * @param key The identifier
     * @param className The entity class
     */
    protected InstanceException(String specificMessage, Object key,
			String className) {

		super(specificMessage + " (key = '" + key + "' - className = '"
				+ className + "')");
		this.key = key;
		this.className = className;

	}

    /**
     *
     * @return the key
     */
    public Object getKey() {
		return key;
	}

    /**
     *
     * @return the entity class
     */
    public String getClassName() {
		return className;
	}

}
