package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;

public class GetOperationsCountAction implements NonTransactionalPlainAction {

	private Long accountId;
	private AccountOperationDAO accountOperationDAO;

	public GetOperationsCountAction(Long accountId) {

		this.accountId = accountId;

	}

	public Object execute(EntityManager entityManager) throws ModelException,
			InternalErrorException {

		initializeDAOs(entityManager);
		return accountOperationDAO.getOperationsCount(accountId);
	}

	private void initializeDAOs(EntityManager entityManager)
			throws InternalErrorException {

		accountOperationDAO = AccountOperationDAOFactory.getDelegate();
		accountOperationDAO.setEntityManager(entityManager);
	}

}
