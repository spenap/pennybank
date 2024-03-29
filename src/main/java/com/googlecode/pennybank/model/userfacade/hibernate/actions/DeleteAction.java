package com.googlecode.pennybank.model.userfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.user.dao.UserDAO;
import com.googlecode.pennybank.model.user.dao.UserDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * An action encapsulating the deletion of an user
 * 
 * @author spenap
 */
public class DeleteAction implements TransactionalPlainAction {

	private Long userId;
	private UserDAO userDAO;

	/**
	 * Creates a new action with the specified parameters
	 * 
	 * @param userId
	 *            The user to be deleted
	 */
	public DeleteAction(Long userId) {
		this.userId = userId;

	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);
		userDAO.remove(userId);
		return null;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		userDAO = UserDAOFactory.getDAO();
		userDAO.setEntityManager(entityManager);
	}

}
