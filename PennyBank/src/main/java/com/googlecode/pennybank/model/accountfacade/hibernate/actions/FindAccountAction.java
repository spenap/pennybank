package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.NonTransactionalPlainAction;

/**
 * Class encapsulating the information needed to find an account
 *
 * @author spenap
 */
public class FindAccountAction implements NonTransactionalPlainAction {

    private Long accountId;
    private AccountDAO accountDAO;

    /**
     * Creates an action with the specified arguments
     *
     * @param accountId The account identifier to search for
     */
    public FindAccountAction(Long accountId) {

        this.accountId = accountId;

    }

    public Object execute(EntityManager entityManager)
            throws ModelException,
            InternalErrorException {

        initializeDAOs(entityManager);

        Account theAccount = accountDAO.find(accountId);
        return theAccount;
    }

    private void initializeDAOs(EntityManager entityManager)
            throws InternalErrorException {

        accountDAO = AccountDAOFactory.getDelegate();
        accountDAO.setEntityManager(entityManager);
    }
}
