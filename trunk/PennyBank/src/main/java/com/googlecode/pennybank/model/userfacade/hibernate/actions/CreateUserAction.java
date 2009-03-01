package com.googlecode.pennybank.model.userfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.user.dao.UserDAO;
import com.googlecode.pennybank.model.user.dao.UserDAOFactory;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * An action encapsulating the creation of an user
 * 
 * @author spenap
 */
public class CreateUserAction implements TransactionalPlainAction {

	private User user;
	private UserDAO userDAO;

    /**
     * Creates a new action with the specified parameters
     * 
     * @param user The user to be created
     */
    public CreateUserAction(User user) {

		this.user = user;

	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);
		userDAO.create(user);
		return user;
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		userDAO = UserDAOFactory.getDAO();
		userDAO.setEntityManager(entityManager);
	}

}
