package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import java.util.Calendar;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.dao.CategoryDAO;
import com.googlecode.pennybank.model.category.dao.CategoryDAOFactory;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Class encapsulating the Add to Account action
 * 
 * @author spenap
 */
public class AddToAccountAction implements TransactionalPlainAction {

	private Long accountId;
	private double amount;
	private String comment;
	private Calendar operationDate;
	private Category category;
	private AccountDAO accountDAO;
	private CategoryDAO categoryDAO;
	private AccountOperationDAO accountOperationDAO;

	/**
	 * Creates an action for adding money to the account with the specified
	 * arguments
	 * 
	 * @param accountId
	 *            The account identifier
	 * @param amount
	 *            The amount to be added
	 * @param comment
	 *            The operation description
	 * @param operationDate
	 *            The operation date
	 * @param category
	 *            The operation category
	 */
	public AddToAccountAction(Long accountId, double amount, String comment,
			Calendar operationDate, Category category) {

		this.accountId = accountId;
		this.amount = amount;
		this.comment = comment;
		this.operationDate = operationDate;
		this.category = category;
	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);

		// Get account
		Account theAccount = accountDAO.find(accountId);

		// Check if category exists
		Category theCategory = null;
		if (category != null)
			theCategory = categoryDAO.find(category.getCategoryId());

		// Update balance
		double balance = theAccount.getBalance();
		theAccount.setBalance(balance + amount);
		accountDAO.update(theAccount);

		// Create account operation
		AccountOperation addOperation = new AccountOperation(theAccount,
				Type.DEPOSIT, amount, operationDate, comment, theCategory);
		accountOperationDAO.create(addOperation);

		return addOperation;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		accountDAO = AccountDAOFactory.getDAO();
		accountOperationDAO = AccountOperationDAOFactory.getDAO();
		categoryDAO = CategoryDAOFactory.getDAO();
		accountDAO.setEntityManager(entityManager);
		accountOperationDAO.setEntityManager(entityManager);
		categoryDAO.setEntityManager(entityManager);
	}
}
