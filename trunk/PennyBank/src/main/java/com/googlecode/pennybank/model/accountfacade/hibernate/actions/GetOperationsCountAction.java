package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;

/**
 * Class encapsulating the information needed to get the number of operations
 * for a given account
 * 
 * @author spenap
 */
public class GetOperationsCountAction implements NonTransactionalPlainAction {

	private Long accountId;
	private AccountOperationDAO accountOperationDAO;
	private AccountDAO accountDAO;

	/**
	 * Creates an action with the specified arguments
	 * 
	 * @param accountId
	 *            The account identifier for the account to search for
	 */
	public GetOperationsCountAction(Long accountId) {

		this.accountId = accountId;

	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);
		accountDAO.find(accountId);

		return accountOperationDAO.getOperationsCount(accountId);
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		accountOperationDAO = AccountOperationDAOFactory.getDelegate();
		accountDAO = AccountDAOFactory.getDelegate();
		accountOperationDAO.setEntityManager(entityManager);
		accountDAO.setEntityManager(entityManager);
	}
}
