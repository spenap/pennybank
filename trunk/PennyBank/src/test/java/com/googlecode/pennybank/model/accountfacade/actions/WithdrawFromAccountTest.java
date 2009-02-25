/**
 * WithdrawFromAccountTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.category.entity.Category;
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
public class WithdrawFromAccountTest {

	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static final long NON_EXISTENT_ACCOUNT_ID = -1;
	private static final long NON_EXISTEN_CATEGORY_ID = -1;
	private static final double INITIAL_BALANCE = 12345.678;
	private static final String ACCOUNT_NAME = "Test Account";
	private static User testUser;
	private static Account testAccount;
	private static Category testCategory;

	/**
	 * Creates a test user and account, to be used in all the tests
	 * 
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

		testCategory = new Category("Test Category");
		testCategory = accountFacade.createCategory(testCategory);
	}

	/**
	 * Deletes the user and account previously created
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		accountFacade.deleteCategory(testCategory.getCategoryId());
		accountFacade.deleteAccount(testAccount.getAccountId());
		userFacade.deleteUser(testUser.getUserId());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testAccount = accountFacade.findAccount(testAccount.getAccountId());
		testAccount.setBalance(INITIAL_BALANCE);
		testAccount = accountFacade.updateAccount(testAccount);
	}

	/**
	 * This method tests if the accountFacade behaves well when trying to
	 * withdraw money from a non existent account
	 * 
	 * @throws InstanceNotFoundException
	 *             The exception expected
	 * @throws NegativeAmountException
	 *             If the amount was negative, not expected
	 * @throws InternalErrorException
	 *             If there was an internal error, not expected
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testWithdrawFromAccountNonExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		accountFacade.withdrawFromAccount(NON_EXISTENT_ACCOUNT_ID, 100,
				"Test subtraction", Calendar.getInstance(), null);
	}

	/**
	 * This method tests if the accountFacade behaves well when trying to
	 * withdraw money from an existent account: that is, the most frequent
	 * operation.
	 * 
	 * @throws InstanceNotFoundException
	 *             If the account couldn't have been found
	 * @throws NegativeAmountException
	 *             If the amount was negative
	 * @throws InternalErrorException
	 *             If another error, not related with the logic, were to happen
	 */
	@Test
	public void testWithdrawFromAccountExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		double subtraction = 300;
		Account theAccount;
		accountFacade.withdrawFromAccount(testAccount.getAccountId(),
				subtraction, "Test subtraction", Calendar.getInstance(), null);
		theAccount = accountFacade.findAccount(testAccount.getAccountId());
		assertTrue(theAccount.getBalance() == INITIAL_BALANCE - subtraction);
	}

	/**
	 * This method tries to withdraw money from an account, classifying it in an
	 * existent category
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testWithdrawFromAccountExistentAccountNonExistentCategory()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		Category nonExistentCategory = new Category("Sample Category");
		nonExistentCategory.setCategoryId(NON_EXISTEN_CATEGORY_ID);
		double subtraction = 300;
		boolean exceptionCatched = false;
		try {
			accountFacade.withdrawFromAccount(testAccount.getAccountId(),
					subtraction, "Test subtraction", Calendar.getInstance(),
					nonExistentCategory);
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

	/**
	 * This method tries to withdraw money from an account, classifying it in an
	 * existent category
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testWithdrawFromAccountExistentAccountExistentCategory()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		double subtraction = 300;
		Account theAccount;
		accountFacade.withdrawFromAccount(testAccount.getAccountId(),
				subtraction, "Test subtraction", Calendar.getInstance(),
				testCategory);
		theAccount = accountFacade.findAccount(testAccount.getAccountId());
		assertTrue(theAccount.getBalance() == INITIAL_BALANCE - subtraction);
	}

	/**
	 * This method tests if the accountFacade behaves well when trying to
	 * withdraw a negative amount of money
	 * 
	 * @throws InstanceNotFoundException
	 *             If the account couldn't have been found
	 * @throws InternalErrorException
	 *             If another error, non related with the logic, were to happen
	 */
	@Test
	public void testWithdrawFromAccountNegativeAmount()
			throws InstanceNotFoundException, InternalErrorException {

		boolean exceptionCatched = false;
		try {
			accountFacade.withdrawFromAccount(testAccount.getAccountId(), -200,
					"Negative subtraction", Calendar.getInstance(), null);
		} catch (NegativeAmountException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}
}
