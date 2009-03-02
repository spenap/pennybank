/**
 * DeleteAccountOperationAction.java
 * 
 * 01/03/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Class encapsulating the information needed to delete an account operation
 * 
 * @author spenap
 */
public class DeleteAccountOperationAction implements TransactionalPlainAction {

	private Long accountOperationId;
	private AccountOperationDAO accountOperationDAO;

	/**
	 * Creates an action with the given arguments
	 * 
	 * @param accountOperationId
	 */
	public DeleteAccountOperationAction(Long accountOperationId) {
		this.accountOperationId = accountOperationId;
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
		AccountOperation theOperation = accountOperationDAO
				.find(accountOperationId);

		// Restore the balance
		Account theAccount = theOperation.getAccount();

		switch (theOperation.getType()) {
		case DEPOSIT:
			theAccount.setBalance(theAccount.getBalance()
					- theOperation.getAmount());
			break;
		case WITHDRAW:
			theAccount.setBalance(theAccount.getBalance()
					+ theOperation.getAmount());
			break;
		default:
			break;
		}

		// Remove the account operation
		accountOperationDAO.remove(theOperation.getOperationIdentifier());

		return null;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		accountOperationDAO = AccountOperationDAOFactory.getDAO();
		accountOperationDAO.setEntityManager(entityManager);
	}

}
