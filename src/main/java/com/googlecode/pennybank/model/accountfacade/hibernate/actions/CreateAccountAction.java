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
 * Class encapsulating the information needed to create an account
 * 
 * @author spenap
 */
public class CreateAccountAction implements TransactionalPlainAction {

	private Account account;
	private AccountDAO accountDAO;
	private UserDAO userDAO;

	/**
	 * Creates a new action with the information needed to actually create the
	 * account
	 * 
	 * @param account
	 *            The account to be created
	 */
	public CreateAccountAction(Account account) {

		this.account = account;

	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);

		userDAO.find(account.getUser().getUserId());
		accountDAO.create(account);
		if (!account.getUser().getAccounts().contains(account)) {
			account.getUser().getAccounts().add(account);
		}
		return account;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		accountDAO = AccountDAOFactory.getDAO();
		userDAO = UserDAOFactory.getDAO();
		userDAO.setEntityManager(entityManager);
		accountDAO.setEntityManager(entityManager);
	}
}
