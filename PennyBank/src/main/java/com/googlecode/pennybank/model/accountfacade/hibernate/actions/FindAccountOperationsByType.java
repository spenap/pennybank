/**
 * FindAccountOperationsByType.java
 * 
 * 28/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import java.util.List;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * Class which encapsulates all the information needed to search account
 * operations for a given operation type
 * 
 * @author spenap
 */
public class FindAccountOperationsByType implements NonTransactionalPlainAction {

	private Long accountId;
	private Type type;
	private int startIndex;
	private int count;
	private AccountDAO accountDAO;
	private AccountOperationDAO accountOperationDAO;

	/**
	 * Creates an action with the specified arguments
	 * 
	 * @param accountId
	 * @param type
	 * @param startIndex
	 * @param count
	 */
	public FindAccountOperationsByType(Long accountId, Type type,
			int startIndex, int count) {
		this.accountId = accountId;
		this.type = type;
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
				.findByOperation(accountId, type, startIndex, count + 1);

		boolean existMore = accountOperations.size() == count + 1;
		if (existMore) {
			accountOperations.remove(accountOperations.size() - 1);
		}

		return new Block<AccountOperation>(existMore, accountOperations);
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		accountDAO = AccountDAOFactory.getDAO();
		accountOperationDAO = AccountOperationDAOFactory.getDAO();

		accountDAO.setEntityManager(entityManager);
		accountOperationDAO.setEntityManager(entityManager);
	}

}
