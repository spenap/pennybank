/**
 * GetOperationsCount.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;

/**
 * @author spenap
 * 
 */
public class GetOperationsCountTest {

	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static final long NON_EXISTENT_ACCOUNT_ID = -1;
	private static final double INITIAL_BALANCE = 12345.678;
	private static final long OPERATIONS_COUNT = 115;
	private static final String ACCOUNT_NAME = "Test Account";
	private static User testUser;
	private static Account testAccount;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();
		testUser = new User("Test User");
		testUser = userFacade.createUser(testUser);
		testAccount = new Account(testUser, INITIAL_BALANCE, ACCOUNT_NAME);
		testAccount = accountFacade.createAccount(testAccount);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		accountFacade.deleteAccount(testAccount.getAccountId());
		userFacade.deleteUser(testUser.getUserId());
	}

	/**
	 * Method which tests the account facade behavior when counting the number
	 * of operations done on an existing account.
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testGetOperationsCountExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {
		for (int i = 0; i < OPERATIONS_COUNT; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), i * i,
					"Test operation " + i, Calendar.getInstance(), null);
		}
		long operationsCount = accountFacade.getOperationsCount(testAccount
				.getAccountId());
		assertEquals(OPERATIONS_COUNT, operationsCount);
	}

	/**
	 * Method which tests the account facade behavior when counting the number
	 * of operations done on a non existing account
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testGetOperationsCountNonExistentAccount()
			throws InstanceNotFoundException, InternalErrorException {
		accountFacade.getOperationsCount(NON_EXISTENT_ACCOUNT_ID);
	}
}
