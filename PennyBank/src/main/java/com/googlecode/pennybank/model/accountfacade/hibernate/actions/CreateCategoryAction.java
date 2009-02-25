/**
 * CreateCategoryAction.java
 * 
 * 24/02/2009
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
 * Action encapsulating the information needed to create a category
 * 
 * @author spenap
 */
public class CreateCategoryAction implements TransactionalPlainAction {

	private Category category;
	private CategoryDAO categoryDAO;

	/**
	 * Creates an action with the specified arguments
	 * 
	 * @param category
	 *            the category to be created
	 */
	public CreateCategoryAction(Category category) {

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

		// Checks if there is a valid parentCategory
		if (category.getParentCategory() != null)
			categoryDAO.find(category.getParentCategory().getCategoryId());

		categoryDAO.create(category);
		return category;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		categoryDAO = CategoryDAOFactory.getDelegate();
		categoryDAO.setEntityManager(entityManager);
	}

}
