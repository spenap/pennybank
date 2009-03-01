package com.googlecode.pennybank.model.cascadebehavior;

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

public class UserCascade {

	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static User theUser;
	private static Account theAccount;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();

		theUser = new User("Test User");
		theUser = userFacade.createUser(theUser);

		theAccount = new Account(theUser, 100, "Test Account");
		theAccount = accountFacade.createAccount(theAccount);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		userFacade.deleteUser(theUser.getUserId());
	}

	@Test
	public void testUserOnUpdate() throws InstanceNotFoundException,
			InternalErrorException {

		// Update the user
		theUser.setName("Updated name");
		theUser = userFacade.updateUser(theUser);

		// Check the accounts
		assertEquals(theUser, theAccount.getUser());
	}
}
