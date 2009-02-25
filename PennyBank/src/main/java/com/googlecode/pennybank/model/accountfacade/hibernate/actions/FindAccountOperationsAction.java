/**
 * FindAccountOperationsAction.java
 * 
 * 25/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.accountfacade.vo.AccountOperationInfo;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * @author spenap
 * 
 */
public class FindAccountOperationsAction implements NonTransactionalPlainAction {

	private long accountId;
	private int startIndex;
	private int count;
	private AccountOperationDAO accountOperationDAO;
	private AccountDAO accountDAO;

	/**
	 * Creates an new action with the specified arguments
	 * 
	 * @param accountId
	 *            The account identifier whose actions we are trying to retrieve
	 * @param startIndex
	 *            The index to start retrieving account operations
	 * @param count
	 *            The number of account operations to retrieve
	 */
	public FindAccountOperationsAction(long accountId, int startIndex, int count) {
		this.accountId = accountId;
		this.startIndex = startIndex;
		this.count = count;
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

		// Check if the account exists
		accountDAO.find(accountId);

		List<AccountOperation> accountOperations = accountOperationDAO
				.findByAccount(accountId, startIndex, count + 1);

		boolean existMore = accountOperations.size() == count + 1;
		if (existMore) {
			accountOperations.remove(accountOperations.size() - 1);
		}

		Block<AccountOperationInfo> accountOperationsInfo = new Block<AccountOperationInfo>(
				existMore, new ArrayList<AccountOperationInfo>());
		for (AccountOperation accountOperation : accountOperations) {
			accountOperationsInfo.getContents()
					.add(
							AccountOperationInfo
									.fromAccountOperation(accountOperation));
		}

		return accountOperationsInfo;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		accountOperationDAO = AccountOperationDAOFactory.getDelegate();
		accountDAO = AccountDAOFactory.getDelegate();
		accountOperationDAO.setEntityManager(entityManager);
		accountDAO.setEntityManager(entityManager);
	}

}
