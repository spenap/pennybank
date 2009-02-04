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

public class CreateAccountAction implements TransactionalPlainAction {

	private Account account;
	private AccountDAO accountDAO;
	private UserDAO userDAO;

	public CreateAccountAction(Account account) {

		this.account = account;

	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);

		userDAO.find(account.getUser().getUserId());
		accountDAO.create(account);
		return account;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		accountDAO = AccountDAOFactory.getDelegate();
		userDAO = UserDAOFactory.getDelegate();
		userDAO.setEntityManager(entityManager);
		accountDAO.setEntityManager(entityManager);
	}

}
