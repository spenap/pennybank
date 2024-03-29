package com.googlecode.pennybank.model.userfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.user.dao.UserDAO;
import com.googlecode.pennybank.model.user.dao.UserDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;

/**
 * An action encapsulating the retrieval of an user
 *
 * @author spenap
 */
public class FindAction implements NonTransactionalPlainAction {

	private Long userId;
	private UserDAO userDAO;

    /**
     * Creates a new action with the specified arguments
     * 
     * @param userId The user identifier to look for
     */
    public FindAction(Long userId) {
		this.userId = userId;
		
	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);

		return userDAO.find(userId);
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		userDAO = UserDAOFactory.getDAO();
		userDAO.setEntityManager(entityManager);
	}

}
