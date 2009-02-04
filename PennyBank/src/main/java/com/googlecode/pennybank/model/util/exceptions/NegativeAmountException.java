package com.googlecode.pennybank.model.util.exceptions;

@SuppressWarnings("serial")
public class NegativeAmountException extends ModelException {

	public NegativeAmountException(double amount) {
		super("Negative amount in operation: " + amount);
	}

}
