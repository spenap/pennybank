/**
 * FindCategoryAction.java
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
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;

/**
 * Class encapsulating the information needed to find a category
 * 
 * @author spenap
 * 
 */
public class FindCategoryAction implements NonTransactionalPlainAction {

	private Long categoryId;
	private CategoryDAO categoryDAO;

	/**
	 * Creates a new action with the specified arguments
	 * 
	 * @param categoryId
	 *            the category identifier
	 */
	public FindCategoryAction(Long categoryId) {
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

		Category theCategory = (Category) categoryDAO.find(categoryId);

		return theCategory;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		categoryDAO = CategoryDAOFactory.getDelegate();
		categoryDAO.setEntityManager(entityManager);
	}

}
