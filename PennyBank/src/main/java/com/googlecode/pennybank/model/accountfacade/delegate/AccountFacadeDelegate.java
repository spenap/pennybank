package com.googlecode.pennybank.model.accountfacade.delegate;

import java.util.Calendar;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.vo.AccountOperationInfo;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * Interface defining the Account Facade
 * 
 * @author spenap
 */
public interface AccountFacadeDelegate {

	/**
	 * Creates a new account with the specified parameters
	 * 
	 * @param account
	 *            The account to be created
	 * @return The account created
	 * @throws InstanceNotFoundException
	 *             if the user who owns the account is not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public Account createAccount(Account account)
			throws InstanceNotFoundException, InternalErrorException;

	/**
	 * Searches for an account with the specified account identifier
	 * 
	 * @param accountId
	 *            The account identifier to search for
	 * @return The account with the account identifier given
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public Account findAccount(Long accountId)
			throws InstanceNotFoundException, InternalErrorException;

	/**
	 * Deletes an account with the account identifier given
	 * 
	 * @param accountId
	 *            The account identifier to search for
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public void deleteAccount(Long accountId) throws InstanceNotFoundException,
			InternalErrorException;

	/**
	 * Updates an account
	 * 
	 * @param account
	 *            The account to search for
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public Account updateAccount(Account account)
			throws InstanceNotFoundException, InternalErrorException;

	/**
	 * Adds a given amount to the account with the account identifier given
	 * 
	 * @param accountId
	 *            The account identifier to search for
	 * @param amount
	 *            The amount to be added to the account
	 * @param comment
	 *            A comment explaining the account operation
	 * @param operationDate
	 *            The operation date
	 * @param category
	 *            Category which classifies the account operation
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 * @throws NegativeAmountException
	 *             if the amount is negative
	 */
	public void addToAccount(Long accountId, double amount, String comment,
			Calendar operationDate, Category category)
			throws InstanceNotFoundException, InternalErrorException,
			NegativeAmountException;

	/**
	 * Withdraws a given amount from the account with the account identifier
	 * given
	 * 
	 * @param accountId
	 *            The account identifier to search for
	 * @param amount
	 *            The amount to be subtracted from the account
	 * @param comment
	 *            A comment explaining the account operation
	 * @param operationDate
	 *            The operation date
	 * @param category
	 *            Category which classifies the account operation
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 * @throws NegativeAmountException
	 *             if the amount is negative
	 */
	public void withdrawFromAccount(Long accountId, double amount,
			String comment, Calendar operationDate, Category category)
			throws InstanceNotFoundException, InternalErrorException,
			NegativeAmountException;

	/**
	 * Transfers a given amount from one account to other
	 * 
	 * @param sourceAccountId
	 *            The account identifier of the source account
	 * @param destinationAccountId
	 *            The account identifier of the destination account
	 * @param amount
	 *            The amount to be transferred between the accounts
	 * @param comment
	 *            A comment explaining the account operation
	 * @param operationDate
	 *            The operation date
	 * @param category
	 *            Category which classifies the account operation
	 * @throws InstanceNotFoundException
	 *             if any of the accounts was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 * @throws NegativeAmountException
	 *             if the amount is negative
	 */
	public void transfer(Long sourceAccountId, Long destinationAccountId,
			double amount, String comment, Calendar operationDate,
			Category category) throws InstanceNotFoundException,
			InternalErrorException, NegativeAmountException;

	/**
	 * Finds all the accounts owned by a specific user
	 * 
	 * @param userId
	 *            The user identifier to look for
	 * @param startIndex
	 *            The index to start retrieving accounts
	 * @param count
	 *            The numbers of accounts to retrieve
	 * @return The block of accounts retrieved
	 * @throws InstanceNotFoundException
	 *             if the user account was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public Block<Account> findAccountByUserId(Long userId, int startIndex,
			int count) throws InternalErrorException, InstanceNotFoundException;

	/**
	 * Finds all the account operations in an account between two dates given
	 * 
	 * @param accountId
	 *            The account identifier of the account
	 * @param startDate
	 *            The start date to look for the account operations
	 * @param endDate
	 *            The end date to stop looking for operations
	 * @param startIndex
	 *            The index to start retrieving account operations
	 * @param count
	 *            The number of account operations to retrieve
	 * @return The block of account operations retrieved
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 */
	public Block<AccountOperationInfo> findAccountOperationsByDate(
			Long accountId, Calendar startDate, Calendar endDate,
			int startIndex, int count) throws InstanceNotFoundException;

	/**
	 * Finds all the account operations in an account
	 * 
	 * @param accountId
	 *            The account identifier to look for
	 * @param startIndex
	 *            The index to start retrieving account operations
	 * @param count
	 *            The number of account operations to retrieve
	 * @return The block of account operations retrieved
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 * @throws InternalErrorException
	 *             if an unexpected error happened
	 */
	public Block<AccountOperationInfo> findAccountOperations(Long accountId,
			int startIndex, int count) throws InstanceNotFoundException,
			InternalErrorException;

	/**
	 * Finds all the account operations for a given type in an account
	 * 
	 * @param accountId
	 *            The account identifier to look for
	 * @param type
	 *            The operation type to look for
	 * @param startIndex
	 *            The index to start retrieving account operations
	 * @param count
	 *            The number of account operations to retrieve
	 * @return The block of account operations retrieved
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 */
	public Block<AccountOperationInfo> findAccountOperationsByType(
			Long accountId, Type type, int startIndex, int count)
			throws InstanceNotFoundException;

	/**
	 * Finds all the account operations for a given category in an account
	 * 
	 * @param accountId
	 *            The account identifier to look for
	 * @param categoryId
	 *            The category identifier to look for
	 * @param startIndex
	 *            The index to start retrieving account operations
	 * @param count
	 *            The number of account operations to retrieve
	 * @return The block of account operations retrieved
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 */
	public Block<AccountOperationInfo> findAccountOperationsByCategory(
			Long accountId, Long categoryId, int startIndex, int count)
			throws InstanceNotFoundException;

	/**
	 * Creates a category with the specified parameters
	 * 
	 * @param category
	 *            The new category to be created
	 * @return The category created
	 * @throws InternalErrorException
	 *             if an unexpected error happened
	 * @throws InstanceNotFoundException
	 *             if the parent category didn't exist
	 * 
	 */
	public Category createCategory(Category category)
			throws InstanceNotFoundException, InternalErrorException;

	/**
	 * Deletes a category with the specified category identifier
	 * 
	 * @param categoryId
	 *            The category identifier to look for
	 * @throws InstanceNotFoundException
	 *             if the category was not found
	 * @throws InternalErrorException
	 *             if an unexpected error happens
	 */
	public void deleteCategory(Long categoryId)
			throws InstanceNotFoundException, InternalErrorException;

	/**
	 * Obtains the number of operations done in a given account
	 * 
	 * @param accountId
	 *            The account identifier to search
	 * @return The number of operations done
	 * @throws InstanceNotFoundException
	 *             if the account was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public Long getOperationsCount(Long accountId)
			throws InstanceNotFoundException, InternalErrorException;

	/**
	 * Finds a category with the given category identifier
	 * 
	 * @param categoryId
	 *            the category identifier to search for
	 */
	public Category findCategory(Long categoryId)
			throws InternalErrorException, InstanceNotFoundException;
}
