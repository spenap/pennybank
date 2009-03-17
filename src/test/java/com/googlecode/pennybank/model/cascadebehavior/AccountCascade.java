package com.googlecode.pennybank.model.cascadebehavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import com.googlecode.pennybank.model.util.vo.Block;

public class AccountCascade {

	private static final double BALANCE = 1000.0;
	private static final double amount = 100;
	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static User theUser;
	private static Account theAccount;
	private static AccountOperation accountOperation;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();

		theUser = new User("CategoryCascade Test");
		theUser = userFacade.createUser(theUser);

		theAccount = new Account(theUser, BALANCE, "CategoryCascade Test");
		theAccount = accountFacade.createAccount(theAccount);

		accountFacade.addToAccount(theAccount.getAccountId(), amount,
				"CategoryCascade Test", Calendar.getInstance(), null);
		Block<AccountOperation> block = accountFacade.findAccountOperations(
				theAccount.getAccountId(), 0, 1);
		accountOperation = block.getContents().get(0);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		userFacade.deleteUser(theUser.getUserId());
	}

	@Test
	public void testAccountOnUpdate() throws InstanceNotFoundException,
			InternalErrorException {

		// Update the account
		theAccount.setName("Updated account");
		theAccount = accountFacade.updateAccount(theAccount);

		// Check the account in the user list
		assertTrue(theUser.getAccounts().contains(theAccount));

		// Check the account in the account operation
		assertEquals(theAccount, accountOperation.getAccount());
	}

	@Test
	public void testAccountOnDelete() throws InstanceNotFoundException,
			InternalErrorException {
		// Delete the account
		accountFacade.deleteAccount(theAccount.getAccountId());

		// Check the account in the user list
		assertFalse(theUser.getAccounts().contains(theAccount));
	}
}
