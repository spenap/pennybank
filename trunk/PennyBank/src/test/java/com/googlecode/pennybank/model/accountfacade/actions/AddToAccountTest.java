/**
 * AddToAccountTest.java
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
 * Tests for the AddToAccount operation.
 * 
 * @author spenap
 */
public class AddToAccountTest {

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
		testAccount.setBalance(INITIAL_BALANCE);
		testAccount = accountFacade.updateAccount(testAccount);
	}

	/**
	 * This method tests if the accountFacade behaves well when trying to add
	 * money to a non existent account
	 * 
	 * @throws InstanceNotFoundException
	 *             The exception expected
	 * @throws NegativeAmountException
	 *             If the amount was negative, not expected
	 * @throws InternalErrorException
	 *             If there was an internal error, not expected
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testAddToAccountNonExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		accountFacade.addToAccount(NON_EXISTENT_ACCOUNT_ID, 100,
				"Test addition", Calendar.getInstance(), null);
	}

	/**
	 * This method tests if the accountFacade behaves well when trying to add
	 * money to an existent account: that is, the most frequent operation.
	 * 
	 * @throws InstanceNotFoundException
	 *             If the account couldn't have been found
	 * @throws NegativeAmountException
	 *             If the amount was negative
	 * @throws InternalErrorException
	 *             If another error, not related with the logic, were to happen
	 */
	@Test
	public void testAddToAccountExistentAccount()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		double addition = 300;
		Account theAccount;
		accountFacade.addToAccount(testAccount.getAccountId(), addition,
				"Test addition", Calendar.getInstance(), null);
		theAccount = accountFacade.findAccount(testAccount.getAccountId());
		assertTrue(theAccount.getBalance() == INITIAL_BALANCE + addition);
	}

	/**
	 * This method tries to add money to an account, classifying it in a
	 * non-existent category
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testAddToAccountExistentAccountNonExistentCategory()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		Category nonExistentCategory = new Category("Sample Category");
		nonExistentCategory.setCategoryId(NON_EXISTEN_CATEGORY_ID);
		double addition = 300;
		boolean exceptionCatched = false;
		try {
			accountFacade.addToAccount(testAccount.getAccountId(), addition,
					"Test addition", Calendar.getInstance(),
					nonExistentCategory);
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

	/**
	 * This method tries to add money to an account, classifying it in an
	 * existent category
	 * 
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 * @throws InternalErrorException
	 */
	@Test
	public void testAddToAccountExistentAccountExistentCategory()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {

		double addition = 300;
		Account theAccount;
		accountFacade.addToAccount(testAccount.getAccountId(), addition,
				"Test addition", Calendar.getInstance(), testCategory);
		theAccount = accountFacade.findAccount(testAccount.getAccountId());
		assertTrue(theAccount.getBalance() == INITIAL_BALANCE + addition);
	}

	/**
	 * This method tests if the accountFacade behaves well when trying to add a
	 * negative amount of money
	 * 
	 * @throws InstanceNotFoundException
	 *             If the account couldn't have been found
	 * @throws InternalErrorException
	 *             If another error, non related with the logic, were to happen
	 */
	@Test
	public void testAddToAccountNegativeAmount()
			throws InstanceNotFoundException, InternalErrorException {

		boolean exceptionCatched = false;
		try {
			accountFacade.addToAccount(testAccount.getAccountId(), -200,
					"Negative addition", Calendar.getInstance(), null);
		} catch (NegativeAmountException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

}
