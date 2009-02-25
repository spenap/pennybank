/**
 * FindAccountOperations.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountfacade.vo.AccountOperationInfo;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * @author spenap
 * 
 */
public class FindAccountOperationsTest {

	private static final double INITIAL_BALANCE = 3000;
	private static final long NON_EXISTENT_ACCOUNT_ID = -1;
	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
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
		testAccount = new Account(testUser, INITIAL_BALANCE, "Test Account");
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
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests if the account facade throws a InstanceNotFound exception when
	 * searching the account operations of a non-existent account
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testFindAccountOperationsNonExistentAccount()
			throws InstanceNotFoundException, InternalErrorException {
		accountFacade.findAccountOperations(NON_EXISTENT_ACCOUNT_ID, 0, 100);
	}

	/**
	 * Method which tests if the findAccountOperations action actually shows if
	 * there are more operations other than those already fetched, when it
	 * should
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 * @throws NegativeAmountException
	 */
	@Test
	public void testFindAccountOperationsExistMore()
			throws InstanceNotFoundException, InternalErrorException,
			NegativeAmountException {
		double amount = 100;
		String comment = "Test operation";
		Calendar operationDate = Calendar.getInstance();
		int operationsNumber = 100;
		for (int i = 0; i < operationsNumber; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), amount,
					comment, operationDate, null);
		}
		Block<AccountOperationInfo> accountOperationsInfo = accountFacade
				.findAccountOperations(testAccount.getAccountId(), 0,
						operationsNumber - 1);
		assertTrue(accountOperationsInfo.isExistMore());
	}

	/**
	 * Method which tests if the action actually shows if there are more
	 * operations other than those already fetched, when it shouldn't
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 * @throws NegativeAmountException
	 */
	@Test
	public void testFindAccountOperationsNotExistMore()
			throws InstanceNotFoundException, InternalErrorException,
			NegativeAmountException {
		double amount = 100;
		String comment = "Test operation";
		Calendar operationDate = Calendar.getInstance();
		int operationsNumber = 100;
		for (int i = 0; i < operationsNumber; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), amount,
					comment, operationDate, null);
		}
		Long readOpCount = accountFacade.getOperationsCount(testAccount
				.getAccountId());
		Block<AccountOperationInfo> accountOperationsInfo = accountFacade
				.findAccountOperations(testAccount.getAccountId(), 0,
						readOpCount.intValue());
		assertFalse(accountOperationsInfo.isExistMore());
	}

	/**
	 * Method which tests if the action retrieves well the number of operations
	 * indicated
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 * @throws NegativeAmountException
	 */
	@Test
	public void testFindAccountOperationsOperationNumber()
			throws InstanceNotFoundException, InternalErrorException,
			NegativeAmountException {
		double amount = 100;
		String comment = "Test operation";
		Calendar operationDate = Calendar.getInstance();
		int operationsNumber = 100;
		for (int i = 0; i < operationsNumber; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), amount,
					comment, operationDate, null);
		}
		Long readOpCount = accountFacade.getOperationsCount(testAccount
				.getAccountId());
		Block<AccountOperationInfo> accountOperationsInfo = accountFacade
				.findAccountOperations(testAccount.getAccountId(), 0,
						readOpCount.intValue());
		assertEquals(readOpCount.intValue(), accountOperationsInfo
				.getContents().size());
	}

}
