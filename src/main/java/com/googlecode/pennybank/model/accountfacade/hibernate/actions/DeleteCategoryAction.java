/**
 * DeleteCategoryAction.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.category.dao.CategoryDAO;
import com.googlecode.pennybank.model.category.dao.CategoryDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Encapsulates the information needed to delete a category
 * 
 * @author spenap
 */
public class DeleteCategoryAction implements TransactionalPlainAction {

	private Long categoryId;
	private CategoryDAO categoryDAO;

	/**
	 * Creates an action with the specified arguments
	 * 
	 * @param categoryId
	 *            The category identifier
	 */
	public DeleteCategoryAction(Long categoryId) {
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
		categoryDAO.remove(categoryId);

		return null;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		categoryDAO = CategoryDAOFactory.getDAO();
		categoryDAO.setEntityManager(entityManager);
	}

}
