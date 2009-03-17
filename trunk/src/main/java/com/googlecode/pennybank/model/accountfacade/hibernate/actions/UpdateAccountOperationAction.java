/**
 * UpdateAccountOperationAction.java
 * 
 * 01/03/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Class which encapsulates the information needed to update an account
 * operation
 * 
 * @author spenap
 */
public class UpdateAccountOperationAction implements TransactionalPlainAction {

	private AccountOperation accountOperation;
	private AccountOperationDAO accountOperationDAO;

	/**
	 * Creates an action with the specified arguments
	 * 
	 * @param accountOperation
	 */
	public UpdateAccountOperationAction(AccountOperation accountOperation) {
		this.accountOperation = accountOperation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.pennybank.model.util.transactions.PlainAction#execute(
	 * javax.persistence.EntityManager)
	 */
	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);

		// Check if the account operation exists
		accountOperationDAO.find(accountOperation.getOperationIdentifier());

		return accountOperationDAO.update(accountOperation);
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		accountOperationDAO = AccountOperationDAOFactory.getDAO();
		accountOperationDAO.setEntityManager(entityManager);
	}

}
