/**
 * FindAccountByUserAction.java
 * 
 * 25/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import java.util.List;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.user.dao.UserDAO;
import com.googlecode.pennybank.model.user.dao.UserDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * @author spenap
 * 
 */
public class FindAccountByUserAction implements NonTransactionalPlainAction {

	private Long userId;
	private int startIndex;
	private int count;
	private UserDAO userDAO;
	private AccountDAO accountDAO;

	/**
	 * Creates an action with the specified parameters
	 * 
	 * @param userId
	 *            The user identifier to search for
	 * @param startIndex
	 *            The index to start retrieving accounts
	 * @param count
	 *            The number of accounts to retrieve
	 */
	public FindAccountByUserAction(Long userId, int startIndex, int count) {
		this.userId = userId;
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

		userDAO.find(userId);

		List<Account> accountList = accountDAO.findByUser(userId, startIndex,
				count + 1);
		boolean existMore = accountList.size() == (count + 1);
		if (existMore)
			accountList.remove(accountList.size() - 1);
		return new Block<Account>(existMore, accountList);
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		userDAO = UserDAOFactory.getDelegate();
		accountDAO = AccountDAOFactory.getDelegate();
		userDAO.setEntityManager(entityManager);
		accountDAO.setEntityManager(entityManager);
	}

}
