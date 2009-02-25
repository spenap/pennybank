/**
 * FindAccountTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;

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

/**
 * @author spenap
 * 
 */
public class FindAccountTest {

	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static final long NON_EXISTENT_ACCOUNT_ID = -1;
	private static final double INITIAL_BALANCE = 12345.678;
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
		userFacade.deleteUser(testUser.getUserId());
	}

	/**
	 * Method which tests the account facade behavior when searching for an
	 * existent account
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testFindExistentAccount() throws InstanceNotFoundException,
			InternalErrorException {
		Account theAccount = accountFacade.findAccount(testAccount
				.getAccountId());
		assertEquals(testAccount, theAccount);
	}

	/**
	 * Method which test the account facade behavior when searching for a
	 * non-existent account
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentAccount() throws InstanceNotFoundException,
			InternalErrorException {
		accountFacade.findAccount(NON_EXISTENT_ACCOUNT_ID);
	}
}
