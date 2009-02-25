/**
 * CreateAccountTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
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
 * Tests for the Create Account method
 * 
 * @author spenap
 */
public class CreateAccountTest {

	private static UserFacadeDelegate userFacade;
	private static User testUser;
	private static AccountFacadeDelegate accountFacade;
	private final long NON_EXISTENT_USER_ID = -1;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
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

	@Before
	public void setUp() {

		try {
			accountFacade = AccountFacadeDelegateFactory.getDelegate();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method which tests if the account facade creates an account, being able
	 * to retrieve it afterwards.
	 * 
	 * @throws InternalErrorException
	 * @throws InstanceNotFoundException
	 */
	@Test
	public void testCreateAccountExistentUser() throws InternalErrorException,
			InstanceNotFoundException {

		Account theAccount = new Account(testUser, 1000, "First account");
		theAccount = accountFacade.createAccount(theAccount);
		Account expectedAccount = accountFacade.findAccount(theAccount
				.getAccountId());

		assertEquals(theAccount, expectedAccount);
	}

	/**
	 * Method which tests if the account facade behaves well when trying to
	 * create an account for a non-existent user
	 * 
	 * @throws InternalErrorException
	 * @throws InstanceNotFoundException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testCreateAccountNonExistentUser()
			throws InternalErrorException, InstanceNotFoundException {

		User fakeUser = new User("Fake User");
		fakeUser.setUserId(NON_EXISTENT_USER_ID);
		Account theAccount = new Account(fakeUser, 1000, "First account");
		accountFacade.createAccount(theAccount);
	}

}
