package com.googlecode.pennybank.model.util.exceptions;

/**
 * Exception notifying of a negative amount
 *
 * @author spenap
 */
@SuppressWarnings("serial")
public class NegativeAmountException extends ModelException {

    /**
     * Creates a new exception with the specified arguments
     * 
     * @param amount The negative amount
     */
    public NegativeAmountException(double amount) {
		super("Negative amount in operation: " + amount);
	}

}
