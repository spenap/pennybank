package com.googlecode.pennybank.model.accountfacade.hibernate.actions;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.dao.AccountDAOFactory;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAOFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.transactions.TransactionalPlainAction;

/**
 * Class which encapsulates the information needed to transfer an amount
 * between accounts
 *
 * @author spenap
 */
public class TransferAction implements TransactionalPlainAction {

    private Long sourceAccountId;
    private Long destinationAccountId;
    private double ammount;
    private String comment;
    private Calendar operationDate;
    private List<Category> categories;
    private AccountDAO accountDAO;
    private AccountOperationDAO accountOperationDAO;

    /**
     * Creates an account with the specified arguments
     *
     * @param sourceAccountId The account identifier for the source account
     * @param destinationAccountId The account identifier for the destination account
     * @param ammount The amount to be transfered
     * @param comment A description for the transfer
     * @param operationDate Operation date
     * @param categories Categories describing this transfer
     */
    public TransferAction(Long sourceAccountId, Long destinationAccountId,
            double ammount, String comment, Calendar operationDate,
            List<Category> categories) {

        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.ammount = ammount;
        this.comment = comment;
        this.operationDate = operationDate;
        this.categories = categories;

    }

    public Object execute(EntityManager entityManager)
            throws ModelException,
            InternalErrorException {

        initializeDAOs(entityManager);

        // Retrieve accounts
        Account sourceAccount = accountDAO.find(sourceAccountId);
        Account destinationAccount = accountDAO.find(destinationAccountId);

        // Update balances
        double sourceBalance = sourceAccount.getBalance();
        double destinationBalance = destinationAccount.getBalance();
        sourceAccount.setBalance(sourceBalance - ammount);
        destinationAccount.setBalance(destinationBalance + ammount);
        accountDAO.update(sourceAccount);
        accountDAO.update(destinationAccount);

        // Create account operations
        AccountOperation withdrawOperation = new AccountOperation(
                sourceAccount, Type.WITHDRAW, ammount, operationDate, comment);
        AccountOperation addOperation = new AccountOperation(
                destinationAccount, Type.DEPOSIT, ammount, operationDate,
                comment);
        accountOperationDAO.create(withdrawOperation);
        accountOperationDAO.create(addOperation);

        return null;
    }

    private void initializeDAOs(EntityManager entityManager)
            throws InternalErrorException {

        accountDAO = AccountDAOFactory.getDelegate();
        accountOperationDAO = AccountOperationDAOFactory.getDelegate();
        accountDAO.setEntityManager(entityManager);
        accountOperationDAO.setEntityManager(entityManager);
    }
}
