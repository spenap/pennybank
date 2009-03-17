/**
 * UpdateAccountTest.java
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
public class UpdateAccountTest {

	private static AccountFacadeDelegate accountFacade;
	private static UserFacadeDelegate userFacade;
	private static User anUser;
	private static User anotherUser;
	private static Account testAccount;
	private static final long NON_EXISTENT_USER_ID = -1;
	private static final double INITIAL_BALANCE = 2000;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		accountFacade = AccountFacadeDelegateFactory.getDelegate();
		userFacade = UserFacadeDelegateFactory.getDelegate();

		anUser = new User("A Test User");
		anUser = userFacade.createUser(anUser);

		anotherUser = new User("Another Test User");
		anotherUser = userFacade.createUser(anotherUser);

		testAccount = new Account(anUser, INITIAL_BALANCE, "Test Account");
		testAccount = accountFacade.createAccount(testAccount);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		accountFacade.deleteAccount(testAccount.getAccountId());
		userFacade.deleteUser(anUser.getUserId());
		userFacade.deleteUser(anotherUser.getUserId());
	}

	/**
	 * This method tests the account facade by trying to update an account
	 * modifying its balance, and checking the correctness of the balence
	 * afterwards.
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testUpdateAccount() throws InstanceNotFoundException,
			InternalErrorException {
		testAccount.setBalance(INITIAL_BALANCE + INITIAL_BALANCE);
		testAccount = accountFacade.updateAccount(testAccount);
		Account theAccount = accountFacade.findAccount(testAccount
				.getAccountId());
		assertEquals(INITIAL_BALANCE + INITIAL_BALANCE, theAccount.getBalance());
	}

	/**
	 * This method tests the account facade by trying to update an account
	 * setting an non-existent user.
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateAccountNonExistentUser()
			throws InstanceNotFoundException, InternalErrorException {
		User theUser = new User("Update User");
		theUser.setUserId(NON_EXISTENT_USER_ID);
		testAccount.setUser(theUser);
		testAccount = accountFacade.updateAccount(testAccount);
	}

	/**
	 * This method tests the account facade by trying to update an account
	 * setting another existing user
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testUpdateAccountExistentUser()
			throws InstanceNotFoundException, InternalErrorException {
		testAccount.setUser(anotherUser);
		testAccount = accountFacade.updateAccount(testAccount);
	}

}
