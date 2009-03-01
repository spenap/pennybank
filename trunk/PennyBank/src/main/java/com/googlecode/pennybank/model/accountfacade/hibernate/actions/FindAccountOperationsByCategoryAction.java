/**
 * FindAccountOperationsByCategoryAction.java
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
import com.googlecode.pennybank.model.category.dao.CategoryDAO;
import com.googlecode.pennybank.model.category.dao.CategoryDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * Class which encapsulates the information needed to search account operations
 * by category
 * 
 * @author spenap
 */
public class FindAccountOperationsByCategoryAction implements
		NonTransactionalPlainAction {

	private Long accountId;
	private int startIndex;
	private int count;
	private Long categoryId;
	private AccountDAO accountDAO;
	private AccountOperationDAO accountOperationDAO;
	private CategoryDAO categoryDAO;

	/**
	 * Creates an action with the specified arguments
	 * 
	 * @param accountId
	 * @param categoryId
	 * @param startIndex
	 * @param count
	 */
	public FindAccountOperationsByCategoryAction(Long accountId,
			Long categoryId, int startIndex, int count) {
		this.accountId = accountId;
		this.startIndex = startIndex;
		this.count = count;
		this.categoryId = categoryId;
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

		// Check if the category exists
		categoryDAO.find(categoryId);

		// Retrieve the account operations
		List<AccountOperation> accountOperations = accountOperationDAO
				.findByCategory(accountId, categoryId, startIndex, count + 1);

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
		categoryDAO = CategoryDAOFactory.getDAO();

		accountDAO.setEntityManager(entityManager);
		accountOperationDAO.setEntityManager(entityManager);
		categoryDAO.setEntityManager(entityManager);
	}

}
