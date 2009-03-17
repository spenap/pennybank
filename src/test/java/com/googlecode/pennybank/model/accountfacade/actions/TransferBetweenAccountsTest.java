/**
 * TransferBetweenAccountsTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

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
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;

/**
 * @author spenap
 * 
 */
public class TransferBetweenAccountsTest {

	private static final double SOURCE_INITIAL_BALANCE = 1000.0;
	private static final double DESTINATION_INITIAL_BALANCE = 2000.0;
	private static final long NON_EXISTENT_ACCOUNT_ID = -1;
	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static Account sourceAccount;
	private static Account destinationAccount;
	private static Account userBAccount;
	private static User testUserA;
	private static User testUserB;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();

		testUserA = new User("Test User A");
		testUserB = new User("Test User B");
		testUserA = userFacade.createUser(testUserA);
		testUserB = userFacade.createUser(testUserB);

		sourceAccount = new Account(testUserA, SOURCE_INITIAL_BALANCE,
				"Source Account");
		destinationAccount = new Account(testUserA,
				DESTINATION_INITIAL_BALANCE, "Destination Account");
		userBAccount = new Account(testUserB, DESTINATION_INITIAL_BALANCE,
				"Destination Account");
		accountFacade.createAccount(sourceAccount);
		accountFacade.createAccount(destinationAccount);
		accountFacade.createAccount(userBAccount);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		accountFacade.deleteAccount(sourceAccount.getAccountId());
		accountFacade.deleteAccount(destinationAccount.getAccountId());
		accountFacade.deleteAccount(userBAccount.getAccountId());
		userFacade.deleteUser(testUserA.getUserId());
		userFacade.deleteUser(testUserB.getUserId());
	}

	@Before
	public void setUpd() throws Exception {
		sourceAccount.setBalance(SOURCE_INITIAL_BALANCE);
		destinationAccount.setBalance(DESTINATION_INITIAL_BALANCE);
		userBAccount.setBalance(DESTINATION_INITIAL_BALANCE);
		accountFacade.updateAccount(sourceAccount);
		accountFacade.updateAccount(destinationAccount);
		accountFacade.updateAccount(userBAccount);
	}

	/**
	 * This method tests transferring founds between a non-existent account and
	 * another
	 * 
	 * @throws InternalErrorException
	 * @throws NegativeAmountException
	 * @throws InstanceNotFoundException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testTransferFromNonExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {
		accountFacade.transfer(NON_EXISTENT_ACCOUNT_ID, destinationAccount
				.getAccountId(), 100, "Test transfer", Calendar.getInstance(),
				null);
	}

	/**
	 * This method tests transferring founds between an existent account and a
	 * non-existent another
	 * 
	 * @throws InternalErrorException
	 * @throws NegativeAmountException
	 * @throws InstanceNotFoundException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testTransferToNonExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {
		accountFacade.transfer(sourceAccount.getAccountId(),
				NON_EXISTENT_ACCOUNT_ID, 100, "Test transfer", Calendar
						.getInstance(), null);
	}

	/**
	 * This method tests transferring negative founds between two existent
	 * accounts
	 * 
	 * @throws InternalErrorException
	 * @throws NegativeAmountException
	 * @throws InstanceNotFoundException
	 */
	@Test(expected = NegativeAmountException.class)
	public void testTransferNegativeAmount() throws InstanceNotFoundException,
			NegativeAmountException, InternalErrorException {
		accountFacade.transfer(sourceAccount.getAccountId(), destinationAccount
				.getAccountId(), -100, "Test transfer", Calendar.getInstance(),
				null);
	}

	/**
	 * This method tests transferring founds between two existent accounts
	 * 
	 * @throws InternalErrorException
	 * @throws NegativeAmountException
	 * @throws InstanceNotFoundException
	 */
	@Test
	public void testTransfer() throws InstanceNotFoundException,
			NegativeAmountException, InternalErrorException {
		double transferAmount = 100;
		accountFacade.transfer(sourceAccount.getAccountId(), destinationAccount
				.getAccountId(), transferAmount, "Test Transfer", Calendar
				.getInstance(), null);
		sourceAccount = accountFacade.findAccount(sourceAccount.getAccountId());
		assertEquals(sourceAccount.getBalance(), SOURCE_INITIAL_BALANCE
				- transferAmount);
		destinationAccount = accountFacade.findAccount(destinationAccount
				.getAccountId());
		assertEquals(destinationAccount.getBalance(),
				DESTINATION_INITIAL_BALANCE + transferAmount);
	}

	/**
	 * This method tests transferring found between two users
	 * 
	 * @throws InternalErrorException
	 * @throws NegativeAmountException
	 * @throws InstanceNotFoundException
	 */
	@Test
	public void testTransferBetweenUsers() throws InstanceNotFoundException,
			NegativeAmountException, InternalErrorException {
		double transferAmount = 100;
		accountFacade.transfer(sourceAccount.getAccountId(), userBAccount
				.getAccountId(), transferAmount, "Test Transfer", Calendar
				.getInstance(), null);
		sourceAccount = accountFacade.findAccount(sourceAccount.getAccountId());
		assertEquals(SOURCE_INITIAL_BALANCE - transferAmount, sourceAccount
				.getBalance());
		userBAccount = accountFacade.findAccount(userBAccount.getAccountId());
		assertEquals(DESTINATION_INITIAL_BALANCE + transferAmount, userBAccount
				.getBalance());
	}

}
