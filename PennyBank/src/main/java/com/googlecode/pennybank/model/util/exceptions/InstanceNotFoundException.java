package com.googlecode.pennybank.model.util.exceptions;

@SuppressWarnings("serial")
public class InstanceNotFoundException extends InstanceException {

	public InstanceNotFoundException(Object key, String className) {
		super("Instance not found", key, className);
	}

}
