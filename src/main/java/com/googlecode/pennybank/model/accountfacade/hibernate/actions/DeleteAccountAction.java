package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Class encapsulating the information needed to delete an account
 *
 * @author spenap
 */
public class DeleteAccountAction implements TransactionalPlainAction {

    private Long accountId;
    private AccountDAO accountDAO;

    /**
     * Creates a new action with the specified arguments
     *
     * @param accountId The identifier for the account to be deleted
     */
    public DeleteAccountAction(Long accountId) {

        this.accountId = accountId;

    }

    public Object execute(EntityManager entityManager)
            throws ModelException,
            InternalErrorException {

        initializeDAOs(entityManager);
        accountDAO.remove(accountId);
        return null;
    }

    private void initializeDAOs(EntityManager entityManager)
            throws InternalErrorException {

        accountDAO = AccountDAOFactory.getDAO();
        accountDAO.setEntityManager(entityManager);
    }
}
