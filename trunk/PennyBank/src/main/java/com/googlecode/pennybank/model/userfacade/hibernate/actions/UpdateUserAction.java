/**
 * UpdateUserAction.java
 * 
 * 28/02/2009
 */
package com.googlecode.pennybank.model.userfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.user.dao.UserDAO;
import com.googlecode.pennybank.model.user.dao.UserDAOFactory;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Class which encapsulates the information needed to update an user
 * 
 * @author spenap
 */
public class UpdateUserAction implements TransactionalPlainAction {

	private User user;
	private UserDAO userDAO;

	/**
	 * Creates an action with the specified arguments
	 * 
	 * @param user
	 */
	public UpdateUserAction(User user) {
		this.user = user;
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

		// Check if the user exists
		userDAO.find(user.getUserId());

		return userDAO.update(user);
	}

	private void initializeDAOs(EntityManager entityManager)
			throws ModelException, InternalErrorException {
		userDAO = UserDAOFactory.getDAO();
		userDAO.setEntityManager(entityManager);
	}

}
