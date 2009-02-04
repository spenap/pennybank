package com.googlecode.pennybank.model.util.exceptions;

@SuppressWarnings("serial")
public class InstanceException extends ModelException {

	private Object key;
	private String className;

	protected InstanceException(String specificMessage, Object key,
			String className) {

		super(specificMessage + " (key = '" + key + "' - className = '"
				+ className + "')");
		this.key = key;
		this.className = className;

	}

	public Object getKey() {
		return key;
	}

	public String getClassName() {
		return className;
	}

}
