package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.model.util.vo.Block;

public class UpdateAccountOperationTest {

	private static final long NON_EXISTENT_OPERATION_ID = -1;
	private static final double INITIAL_BALANCE = 1000.0;
	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static Account theAccount;
	private static User theUser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();

		theUser = new User("Test User");
		theUser = userFacade.createUser(theUser);

		theAccount = new Account(theUser, INITIAL_BALANCE, "Test Account");
		theAccount = accountFacade.createAccount(theAccount);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		accountFacade.deleteAccount(theAccount.getAccountId());
		userFacade.deleteUser(theUser.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		accountFacade.deleteAccount(theAccount.getAccountId());
		theAccount = new Account(theUser,INITIAL_BALANCE,"Test Account");
		theAccount = accountFacade.createAccount(theAccount);
	}

	/**
	 * Method which tests if the account facade behaves well when asked to
	 * update an account operation, setting the correct account balance
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testUpdateAccountOperationCheckBalance()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {
		// Create the account operation
		double amount = 200;
		accountFacade.addToAccount(theAccount.getAccountId(), amount,
				"Test Addition", Calendar.getInstance(), null);
		// Check the addition
		assertEquals(INITIAL_BALANCE + amount, theAccount.getBalance());

		// Get the operation
		Block<AccountOperation> operations = accountFacade
				.findAccountOperations(theAccount.getAccountId(), 0, 1);
		AccountOperation theOperation = operations.getContents().get(0);

		// Update the operation
		double updatedAmount = 300;
		theOperation.setAmount(updatedAmount);
		accountFacade.updateAccountOperation(theOperation);

		// Check the balance
		assertEquals(INITIAL_BALANCE + updatedAmount, theAccount.getBalance());
	}

	/**
	 * Method which tests if the account facade behaves well when asked to
	 * update an operation, effectively updating that operation
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testUpdateAccountOperationCheckOperations()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {
		// Create the account operation
		double amount = 200;
		accountFacade.addToAccount(theAccount.getAccountId(), amount,
				"Test Addition", Calendar.getInstance(), null);
		// Check the addition
		assertEquals(INITIAL_BALANCE + amount, theAccount.getBalance());

		// Get the operation
		Block<AccountOperation> operations = accountFacade
				.findAccountOperations(theAccount.getAccountId(), 0, 1);
		AccountOperation theOperation = operations.getContents().get(0);

		// Update the operation
		double updatedAmount = 50;
		theOperation.setAmount(updatedAmount);
		accountFacade.updateAccountOperation(theOperation);

		// Check the operation number
		operations = accountFacade.findAccountOperations(theAccount
				.getAccountId(), 0, 1);
		assertEquals(1, operations.getContents().size());
		assertFalse(operations.isExistMore());
	}

	/**
	 * Method which tests if the delete account operation action behaves well
	 * when asked to update a non-existent account operation
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateAccountOperationNonExistent()
			throws InstanceNotFoundException, InternalErrorException {
		AccountOperation operation = new AccountOperation();
		operation.setOperationIdentifier(NON_EXISTENT_OPERATION_ID);
		accountFacade.updateAccountOperation(operation);
	}

}
