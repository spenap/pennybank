/**
 * FindAllCategoriesAction.java
 * 
 * 03/03/2009
 */
package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.category.dao.CategoryDAO;
import com.googlecode.pennybank.model.category.dao.CategoryDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;

/**
 * @author spenap
 */
public class FindAllCategoriesAction implements NonTransactionalPlainAction {

	private CategoryDAO categoryDAO;

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {
		categoryDAO = CategoryDAOFactory.getDAO();
		categoryDAO.setEntityManager(entityManager);
	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {
		initializeDAOs(entityManager);

		return categoryDAO.findAll();
	}

}
