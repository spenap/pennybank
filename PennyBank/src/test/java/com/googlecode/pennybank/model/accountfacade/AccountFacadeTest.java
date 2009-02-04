package com.googlecode.pennybank.model.accountfacade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
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

public class AccountFacadeTest {

	private UserFacadeDelegate userFacade;
	private AccountFacadeDelegate accountFacade;
	private final long NON_EXISTENT_ACCOUNT_ID = -1;
	private final long NON_EXISTENT_USER_ID = -1;
	private User testUser;

	@Before
	public void setUp() {

		try {
			userFacade = UserFacadeDelegateFactory.getDelegate();
			accountFacade = AccountFacadeDelegateFactory.getDelegate();

			testUser = new User("Test User");
			userFacade.createUser(testUser);
		} catch (InternalErrorException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {

		try {
			userFacade.deleteUser(testUser.getUserId());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateAccountExistentUser() throws InternalErrorException,
			InstanceNotFoundException {

		Account theAccount = new Account(testUser, 1000, "First account");
		accountFacade.createAccount(theAccount);
		Account expectedAccount = accountFacade.findAccount(theAccount
				.getAccountId());

		assertEquals(theAccount, expectedAccount);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testCreateAccountNonExistentUser()
			throws InternalErrorException, InstanceNotFoundException {

		User fakeUser = new User("Fake User");
		fakeUser.setUserId(NON_EXISTENT_USER_ID);
		Account theAccount = new Account(fakeUser, 1000, "First account");
		accountFacade.createAccount(theAccount);
	}

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

	@Test(expected = InstanceNotFoundException.class)
	public void testAddToAccountNonExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		accountFacade.addToAccount(NON_EXISTENT_ACCOUNT_ID, 100,
				"Test addition", Calendar.getInstance(), null);
	}

	@Test
	public void testAddToAccountExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		double initialBalance = 1000;
		double addition = 100;
		Account theAccount = new Account(testUser, initialBalance,
				"Test Account");
		theAccount = accountFacade.createAccount(theAccount);
		accountFacade.addToAccount(theAccount.getAccountId(), addition,
				"Test addition", Calendar.getInstance(), null);
		theAccount = accountFacade.findAccount(theAccount.getAccountId());
		assertTrue(theAccount.getBalance() == initialBalance + addition);

		long operationsCount = accountFacade.getOperationsCount(theAccount
				.getAccountId());
		assertTrue(operationsCount == 1);
	}

	@Test
	public void testAddToAccountNegativeAmount()
			throws InstanceNotFoundException, InternalErrorException {

		boolean exceptionCatched = false;
		Account theAccount = new Account(testUser, 1000, "Test Account");
		theAccount = accountFacade.createAccount(theAccount);
		try {
			accountFacade.addToAccount(theAccount.getAccountId(), -200,
					"Negative addition", Calendar.getInstance(), null);
		} catch (NegativeAmountException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testWithdrawFromAccountNonExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		accountFacade.withdrawFromAccount(NON_EXISTENT_ACCOUNT_ID, 1000,
				"Test withdrawal", Calendar.getInstance(), null);
	}

	@Test
	public void testWithdrawFromAccountExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		double initialBalance = 1000;
		double amount = 233;

		Account theAccount = new Account(testUser, initialBalance,
				"Test Account");
		theAccount = accountFacade.createAccount(theAccount);
		accountFacade.withdrawFromAccount(theAccount.getAccountId(), amount,
				"Test Withdrawal", Calendar.getInstance(), null);

		theAccount = accountFacade.findAccount(theAccount.getAccountId());
		assertEquals(theAccount.getBalance(), initialBalance - amount);

		long opCount = accountFacade.getOperationsCount(theAccount
				.getAccountId());
		assertTrue(opCount == 1);
	}

	@Test(expected = NegativeAmountException.class)
	public void testWithdrawFromAccountNegativeAmount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		Account theAccount = new Account(testUser, 1000, "Test Account");
		theAccount = accountFacade.createAccount(theAccount);

		accountFacade.withdrawFromAccount(theAccount.getAccountId(), -1000,
				"Test Withdrawal", Calendar.getInstance(), null);
	}

}