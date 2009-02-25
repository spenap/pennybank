/**
 * DeleteAccountTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertTrue;

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
public class DeleteAccountTest {

	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static User testUser;
	private static final long NON_EXISTENT_ACCOUNT_ID = -1;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();
		testUser = new User("Test User");
		testUser = userFacade.createUser(testUser);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		userFacade.deleteUser(testUser.getUserId());
	}

	/**
	 * Method for testing the deletion of an existent account
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testDeleteAccountExistentAccount()
			throws InstanceNotFoundException, InternalErrorException {

		Account theAccount = new Account(testUser, 1000, "First Account");
		theAccount = accountFacade.createAccount(theAccount);
		accountFacade.deleteAccount(theAccount.getAccountId());
		boolean exceptionCatched = false;
		try {
			accountFacade.findAccount(theAccount.getAccountId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

	/**
	 * Method for testing the deletion of a non-existent account
	 * 
	 * @throws InternalErrorException
	 */
	@Test
	public void testDeleteAccountNonExistentAccount()
			throws InternalErrorException {

		boolean exceptionCatched = false;
		try {
			accountFacade.deleteAccount(NON_EXISTENT_ACCOUNT_ID);
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

}
