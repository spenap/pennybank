package com.googlecode.pennybank.model.accountfacade.hibernate;

import java.util.Calendar;
import java.util.List;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.AddToAccountAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.CreateAccountAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.CreateCategoryAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.DeleteAccountAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.DeleteAccountOperationAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.DeleteCategoryAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.FindAccountAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.FindAccountOperationsAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.FindAccountOperationsByCategoryAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.FindAccountOperationsByDate;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.FindAccountOperationsByType;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.FindAllCategoriesAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.FindCategoryAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.GetOperationsCountAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.TransferAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.UpdateAccountAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.UpdateAccountOperationAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.UpdateCategoryAction;
import com.googlecode.pennybank.model.accountfacade.hibernate.actions.WithdrawFromAccountAction;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.model.util.facade.HibernateFacade;
import com.googlecode.pennybank.model.util.transactions.PlainActionProcessor;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * Delegate implementing the AccountFacadeDelegate interface, using Hibernate
 * 
 * @author spenap
 */
public class AccountFacade extends HibernateFacade implements
		AccountFacadeDelegate {

	public void addToAccount(Long accountId, double amount, String comment,
			Calendar operationDate, Category category)
			throws InstanceNotFoundException, InternalErrorException,
			NegativeAmountException {

		if (amount < 0) {
			throw new NegativeAmountException(amount);
		}

		AddToAccountAction action = new AddToAccountAction(accountId, amount,
				comment, operationDate, category);
		try {
			PlainActionProcessor.process(entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public Account createAccount(Account account)
			throws InternalErrorException, InstanceNotFoundException {

		CreateAccountAction action = new CreateAccountAction(account);

		Account theAccount;
		try {
			theAccount = (Account) PlainActionProcessor.process(entityManager,
					action);
			return theAccount;
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public Category createCategory(Category category)
			throws InstanceNotFoundException, InternalErrorException {

		CreateCategoryAction action = new CreateCategoryAction(category);
		try {
			return (Category) PlainActionProcessor.process(entityManager,
					action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public void deleteAccount(Long accountId) throws InstanceNotFoundException,
			InternalErrorException {

		DeleteAccountAction action = new DeleteAccountAction(accountId);
		try {
			PlainActionProcessor.process(entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public void deleteAccountOperation(Long accountOperationId)
			throws InstanceNotFoundException, InternalErrorException {
		DeleteAccountOperationAction action = new DeleteAccountOperationAction(
				accountOperationId);
		try {
			PlainActionProcessor.process(entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public void deleteCategory(Long categoryId)
			throws InstanceNotFoundException, InternalErrorException {

		DeleteCategoryAction action = new DeleteCategoryAction(categoryId);
		try {
			PlainActionProcessor.process(entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public Account findAccount(Long accountId)
			throws InstanceNotFoundException, InternalErrorException {

		FindAccountAction action = new FindAccountAction(accountId);

		try {
			return (Account) PlainActionProcessor
					.process(entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}

	}

	@SuppressWarnings("unchecked")
	public Block<AccountOperation> findAccountOperations(Long accountId,
			int startIndex, int count) throws InstanceNotFoundException,
			InternalErrorException {

		FindAccountOperationsAction action = new FindAccountOperationsAction(
				accountId, startIndex, count);
		try {
			return (Block<AccountOperation>) PlainActionProcessor.process(
					entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Block<AccountOperation> findAccountOperationsByCategory(
			Long accountId, Long categoryId, int startIndex, int count)
			throws InstanceNotFoundException, InternalErrorException {

		FindAccountOperationsByCategoryAction action = new FindAccountOperationsByCategoryAction(
				accountId, categoryId, startIndex, count);
		try {
			return (Block<AccountOperation>) PlainActionProcessor.process(
					entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Block<AccountOperation> findAccountOperationsByDate(Long accountId,
			Calendar startDate, Calendar endDate, int startIndex, int count)
			throws InstanceNotFoundException, InternalErrorException {

		FindAccountOperationsByDate action = new FindAccountOperationsByDate(
				accountId, startDate, endDate, startIndex, count);

		try {
			return (Block<AccountOperation>) PlainActionProcessor.process(
					entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Block<AccountOperation> findAccountOperationsByType(Long accountId,
			Type type, int startIndex, int count)
			throws InstanceNotFoundException, InternalErrorException {

		FindAccountOperationsByType action = new FindAccountOperationsByType(
				accountId, type, startIndex, count);

		try {
			return (Block<AccountOperation>) PlainActionProcessor.process(
					entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public Category findCategory(Long categoryId)
			throws InternalErrorException, InstanceNotFoundException {

		FindCategoryAction action = new FindCategoryAction(categoryId);

		try {
			return (Category) PlainActionProcessor.process(entityManager,
					action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public Long getOperationsCount(Long accountId)
			throws InstanceNotFoundException, InternalErrorException {

		GetOperationsCountAction action = new GetOperationsCountAction(
				accountId);
		try {
			return (Long) PlainActionProcessor.process(entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public void transfer(Long sourceAccountId, Long destinationAccountId,
			double amount, String comment, Calendar operationDate,
			Category category) throws InstanceNotFoundException,
			InternalErrorException, NegativeAmountException {

		if (amount < 0) {
			throw new NegativeAmountException(amount);
		}

		TransferAction action = new TransferAction(sourceAccountId,
				destinationAccountId, amount, comment, operationDate, category);
		try {
			PlainActionProcessor.process(entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}

	}

	public Account updateAccount(Account account)
			throws InternalErrorException, InstanceNotFoundException {
		UpdateAccountAction action = new UpdateAccountAction(account);
		Account theAccount;
		try {
			theAccount = (Account) PlainActionProcessor.process(entityManager,
					action);
			return theAccount;
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public AccountOperation updateAccountOperation(
			AccountOperation accountOperation)
			throws InstanceNotFoundException, InternalErrorException {
		UpdateAccountOperationAction action = new UpdateAccountOperationAction(
				accountOperation);
		try {
			return (AccountOperation) PlainActionProcessor.process(
					entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public Category updateCategory(Category category)
			throws InstanceNotFoundException, InternalErrorException {
		UpdateCategoryAction action = new UpdateCategoryAction(category);
		try {
			return (Category) PlainActionProcessor.process(entityManager,
					action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	public void withdrawFromAccount(Long accountId, double amount,
			String comment, Calendar operationDate, Category category)
			throws InstanceNotFoundException, InternalErrorException,
			NegativeAmountException {

		if (amount < 0) {
			throw new NegativeAmountException(amount);
		}

		WithdrawFromAccountAction action = new WithdrawFromAccountAction(
				accountId, amount, comment, operationDate, category);
		try {
			PlainActionProcessor.process(entityManager, action);
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Category> findAllCategories() throws InternalErrorException {
		FindAllCategoriesAction action = new FindAllCategoriesAction();
		try {
			return (List<Category>) PlainActionProcessor.process(entityManager,
					action);
		} catch (ModelException e) {
			throw new InternalErrorException(e);
		}
	}
}