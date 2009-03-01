/**
 * FindAccountOperationsByTypeTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * Test case checking the correctness of the FindAccountOperationsByType method
 * in the account facade
 * 
 * @author spenap
 */
public class FindAccountOperationsByTypeTest {

	private static final double INITIAL_BALANCE = 0;
	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static User testUser;
	private static Account testAccount;
	private static int additionsMade;
	private static int withdrawalsMade;

	/**
	 * Creates all the entities needed to make the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();

		testUser = new User("Test user");
		userFacade.createUser(testUser);

		testAccount = new Account(testUser, INITIAL_BALANCE, "Test account");
		accountFacade.createAccount(testAccount);

		Random random = new Random();
		double amount = 123.4;
		additionsMade = random.nextInt(30);
		withdrawalsMade = random.nextInt(30);

		for (int i = 0; i < additionsMade; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), amount,
					"Addition " + i, Calendar.getInstance(), null);
		}

		for (int i = 0; i < withdrawalsMade; i++) {
			accountFacade.withdrawFromAccount(testAccount.getAccountId(),
					amount, "Withdrawal " + i, Calendar.getInstance(), null);
		}
	}

	/**
	 * Removes all the entities created in the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		accountFacade.deleteAccount(testAccount.getAccountId());
		userFacade.deleteUser(testUser.getUserId());
	}

	/**
	 * Method which tests if the account facade behaves well when asked to
	 * retrieve all the additions made to an account
	 * 
	 * @throws InternalErrorException
	 * @throws InstanceNotFoundException
	 */
	@Test
	public void testFindAdditions() throws InstanceNotFoundException,
			InternalErrorException {
		Long opCount = accountFacade.getOperationsCount(testAccount
				.getAccountId());
		Block<AccountOperation> accountOperations = accountFacade
				.findAccountOperationsByType(testAccount.getAccountId(),
						Type.DEPOSIT, 0, opCount.intValue());
		for (AccountOperation operation : accountOperations.getContents()) {
			assertEquals(Type.DEPOSIT, operation.getType());
		}
		assertEquals(additionsMade, accountOperations.getContents().size());
	}

	/**
	 * Method which tests if the account facade behaves well when asked to
	 * retrieve all the withdrawals made from an account
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testFindWithdrawals() throws InstanceNotFoundException,
			InternalErrorException {
		Long opCount = accountFacade.getOperationsCount(testAccount
				.getAccountId());
		Block<AccountOperation> accountOperations = accountFacade
				.findAccountOperationsByType(testAccount.getAccountId(),
						Type.WITHDRAW, 0, opCount.intValue());
		for (AccountOperation operation : accountOperations.getContents()) {
			assertEquals(Type.WITHDRAW, operation.getType());
		}
		assertEquals(withdrawalsMade, accountOperations.getContents().size());
	}

	/**
	 * Method which tests if the account operations block returned by the
	 * account facade correctly informs of the existing items not retrieved.
	 * Asked to retrieve count - 1 items, it should answer that there are more
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testFindOperationsExistMore() throws InstanceNotFoundException,
			InternalErrorException {
		Block<AccountOperation> accountOperations = accountFacade
				.findAccountOperationsByType(testAccount.getAccountId(),
						Type.WITHDRAW, 0, withdrawalsMade - 1);
		for (AccountOperation operation : accountOperations.getContents()) {
			assertEquals(Type.WITHDRAW, operation.getType());
		}
		assertTrue(accountOperations.isExistMore());
	}

	/**
	 * Method which tests if the account operations block returned by the
	 * account facade correctly informs of the existing items not retrieved.
	 * Asked to retrieve count items, it should answer that there are not more
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testFindOperationsNotExistMore()
			throws InstanceNotFoundException, InternalErrorException {
		Block<AccountOperation> accountOperations = accountFacade
				.findAccountOperationsByType(testAccount.getAccountId(),
						Type.DEPOSIT, 0, additionsMade);
		for (AccountOperation operation : accountOperations.getContents()) {
			assertEquals(Type.DEPOSIT, operation.getType());
		}
		assertFalse(accountOperations.isExistMore());
	}
}
