package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

public class WithdrawFromAccountAction implements TransactionalPlainAction {

	private Long accountId;
	private double amount;
	private String comment;
	private Calendar operationDate;
	private List<Category> categories;
	private AccountDAO accountDAO;
	private AccountOperationDAO accountOperationDAO;

	public WithdrawFromAccountAction(Long accountId, double amount,
			String comment, Calendar operationDate, List<Category> categories) {

		this.accountId = accountId;
		this.amount = amount;
		this.comment = comment;
		this.operationDate = operationDate;
		this.categories = categories;

	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);

		// Retrieve the account
		Account theAccount = accountDAO.find(accountId);

		// Update balance
		double balance = theAccount.getBalance();
		theAccount.setBalance(balance - amount);
		accountDAO.update(theAccount);

		// Create account operation
		AccountOperation withdrawOperation = new AccountOperation(theAccount,
				Type.WITHDRAW, amount, operationDate, comment);
		accountOperationDAO.create(withdrawOperation);

		return null;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		accountDAO = AccountDAOFactory.getDelegate();
		accountOperationDAO = AccountOperationDAOFactory.getDelegate();
		accountDAO.setEntityManager(entityManager);
		accountOperationDAO.setEntityManager(entityManager);
	}

}
