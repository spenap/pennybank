package com.googlecode.pennybank.model.util.transactions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;

/**
 * The PlainAction interface
 *
 * @author spenap
 */
public interface PlainAction {

    /**
     * Method to be implemented for all the PlainActions. It represents the
     * execution of the action itself.
     *
     * @param entityManager The entity manager
     * @return An object encapsulating the action results
     * @throws ModelException If an exception regarding the model is raised
     * @throws InternalErrorException If an unexpected exception is raised
     */
    Object execute(EntityManager entityManager)
            throws ModelException,
            InternalErrorException;
}
