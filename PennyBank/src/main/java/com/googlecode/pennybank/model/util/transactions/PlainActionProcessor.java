package com.googlecode.pennybank.model.util.transactions;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;

/**
 * A utility class to execute plain actions.
 */
public final class PlainActionProcessor {

	private PlainActionProcessor() {

	}

	/**
	 * Processes an action, obtaining its results
	 * 
	 * @param entityManager
	 *            The entity manager
	 * @param action
	 *            The non transactional plain action to execute
	 * @return The execution results
	 * @throws ModelException
	 *             A model-related exception
	 * @throws InternalErrorException
	 *             If an unexpected error happens
	 */
	public final static Object process(EntityManager entityManager,
			NonTransactionalPlainAction action) throws ModelException,
			InternalErrorException {

		try {
			Object toreturn = action.execute(entityManager);
			return toreturn;
		} catch (ModelException e) {
			throw e;
		} catch (InternalErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}

	}

	/**
	 * Processes an action, obtaining its results
	 * 
	 * @param entityManager
	 *            The entity manager
	 * @param action
	 *            The transactional plain action to be executed
	 * @return The execution results
	 * @throws ModelException
	 *             A model-related exception
	 * @throws InternalErrorException
	 *             If an unexpected error happens
	 */
	public final static Object process(EntityManager entityManager,
			TransactionalPlainAction action) throws ModelException,
			InternalErrorException {

		EntityTransaction entityTransaction = null;
		boolean rollback = false;

		try {
			/* Creates the transaction */
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();

			/* Execute action. */
			Object result = action.execute(entityManager);

			/* Return "result". */
			return result;

		} catch (InternalErrorException e) {
			rollback = true;
			throw e;
		} catch (ModelException e) {
			rollback = true;
			throw e;
		} catch (RuntimeException e) {
			rollback = true;
			throw new InternalErrorException(e);
		} catch (Error e) {
			rollback = true;
			throw e;
		} finally {

			/* Commit or rollback, and finally, close connection. */
			if (rollback) {
				if (entityTransaction != null && entityTransaction.isActive()) {
					entityTransaction.rollback();

				}

			} else {
				entityTransaction.commit();
			}

		}

	}

}
