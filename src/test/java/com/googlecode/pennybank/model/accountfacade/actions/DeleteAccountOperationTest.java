/**
 * DeleteAccountOperationTest.java
 * 
 * 01/03/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Calendar;

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

/**
 * Class which tests the behavior of the DeleteAccountOperation method in the
 * account facade
 * 
 * @author spenap
 */
public class DeleteAccountOperationTest {

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

	/**
	 * Method which tests if the account facade behaves well when asked to
	 * delete an account operation, setting the account balance as if the
	 * operation didn't have existed
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testDeleteAccountOperationCheckBalance()
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

		// Delete the operation
		accountFacade.deleteAccountOperation(theOperation
				.getOperationIdentifier());

		// Check the balance
		assertEquals(INITIAL_BALANCE, theAccount.getBalance());
	}

	/**
	 * Method which tests if the account facade behaves well when asked to
	 * delete an operation, effectively deleting that operation
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testDeleteAccountOperationCheckOperations()
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

		// Delete the operation
		accountFacade.deleteAccountOperation(theOperation
				.getOperationIdentifier());

		// Check the operation number
		operations = accountFacade.findAccountOperations(theAccount
				.getAccountId(), 0, 1);
		assertEquals(0, operations.getContents().size());
		assertFalse(operations.isExistMore());
	}

	/**
	 * Method which tests if the delete account operation action behaves well
	 * when asked to delete a non-existent account operation
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testDeleteAccountOperationNonExistent()
			throws InstanceNotFoundException, InternalErrorException {
		accountFacade.deleteAccountOperation(NON_EXISTENT_OPERATION_ID);
	}
}
