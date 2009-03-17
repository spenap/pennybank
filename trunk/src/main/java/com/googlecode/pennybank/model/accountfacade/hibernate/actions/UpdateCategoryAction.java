/**
 * UpdateCategoryAction.java
 * 
 * 28/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.category.dao.CategoryDAO;
import com.googlecode.pennybank.model.category.dao.CategoryDAOFactory;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Class encapsulating all the information needed to update a category
 * 
 * @author spenap
 * 
 */
public class UpdateCategoryAction implements TransactionalPlainAction {

	private Category category;
	private CategoryDAO categoryDAO;

	/**
	 * Creates a new action with the specified arguments
	 * 
	 * @param categoryId
	 */
	public UpdateCategoryAction(Category category) {
		this.category = category;
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

		// Checks if the category exists
		categoryDAO.find(category.getCategoryId());

		return categoryDAO.update(category);

	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		categoryDAO = CategoryDAOFactory.getDAO();
		categoryDAO.setEntityManager(entityManager);
	}

}
