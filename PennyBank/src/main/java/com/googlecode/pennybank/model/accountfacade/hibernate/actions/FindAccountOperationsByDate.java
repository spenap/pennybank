/**
 * FindAccountOperationsByDate.java
 * 
 * 01/03/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * Class encapsulating the information needed to find the account operations
 * made between two dates
 * 
 * @author spenap
 */
public class FindAccountOperationsByDate implements NonTransactionalPlainAction {

	private Long accountId;
	private Calendar startDate;
	private Calendar endDate;
	private int startIndex;
	private int count;
	private AccountDAO accountDAO;
	private AccountOperationDAO accountOperationDAO;

	/**
	 * Creates a new action with the specified arguments
	 * 
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @param startIndex
	 * @param count
	 */
	public FindAccountOperationsByDate(Long accountId, Calendar startDate,
			Calendar endDate, int startIndex, int count) {
		this.accountId = accountId;
		this.startDate = Calendar.getInstance();
		this.startDate.setTime(startDate.getTime());
		this.endDate = Calendar.getInstance();
		this.endDate.setTime(endDate.getTime());
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

		// Get the operations
		List<AccountOperation> accountOperations = accountOperationDAO
				.findByDate(accountId, startDate, endDate, startIndex,
						count + 1);
		boolean existMore = accountOperations.size() == count + 1;
		if (existMore) {
			accountOperations.remove(accountOperations.size() - 1);
		}

		return new Block<AccountOperation>(existMore, accountOperations);
	}

	private void initializeDAOs(EntityManager entityManager)
			throws ModelException, InternalErrorException {
		accountDAO = AccountDAOFactory.getDAO();
		accountOperationDAO = AccountOperationDAOFactory.getDAO();

		accountDAO.setEntityManager(entityManager);
		accountOperationDAO.setEntityManager(entityManager);
	}

}
