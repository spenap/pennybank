/**
 * UpdateAccountAction.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.user.dao.UserDAO;
import com.googlecode.pennybank.model.user.dao.UserDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Class encapsulating the information needed to update an account
 * 
 * @author spenap
 * 
 */
public class UpdateAccountAction implements TransactionalPlainAction {

	private Account account;
	private AccountDAO accountDAO;
	private UserDAO userDAO;

	/**
	 * Creates a new action with the information needed to actually update the
	 * account
	 * 
	 * @param account
	 *            The account to be updated
	 */
	public UpdateAccountAction(Account account) {
		this.account = account;
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

		// Check if the user is valid
		userDAO.find(account.getUser().getUserId());

		account = accountDAO.update(account);
		return account;

	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		accountDAO = AccountDAOFactory.getDAO();
		userDAO = UserDAOFactory.getDAO();
		accountDAO.setEntityManager(entityManager);
		userDAO.setEntityManager(entityManager);
	}

}
