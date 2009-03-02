/**
 * FindAccountByUser.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * This class groups all the tests made to check the behavior of the
 * FindAccountOperationsByCategory method in the AccountFacade
 * 
 * @author spenap
 * 
 */
public class FindAccountOperationsByCategoryTest {

	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static User testUser;
	private static Account testAccount;
	private static Category testRootCategory;
	private static Category testLeafCategory;
	private static final long NON_EXISTENT_CATEGORY_ID = -1;
	private static final double INITIAL_BALANCE = 0;

	/**
	 * Creates a test user, a test account, and two categories: parent and
	 * child.
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();

		testUser = new User("Test User");
		testUser = userFacade.createUser(testUser);

		testAccount = new Account(testUser, INITIAL_BALANCE, "Test Account");
		testAccount = accountFacade.createAccount(testAccount);

		testRootCategory = new Category("Test Root Category");
		testLeafCategory = new Category("Test Leaf Category", testRootCategory);
		testRootCategory = accountFacade.createCategory(testRootCategory);
		testLeafCategory = accountFacade.createCategory(testLeafCategory);
	}

	/**
	 * Deletes the objects created for the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		accountFacade.deleteCategory(testLeafCategory.getCategoryId());
		accountFacade.deleteCategory(testRootCategory.getCategoryId());
		userFacade.deleteUser(testUser.getUserId());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This method tests if the Block of AccountOperationInfo returned by the
	 * account facade correctly informs if there are more account operations
	 * left.
	 * 
	 * @throws InternalErrorException
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 */
	@Test
	public void testFindOperationsByCategoryExistMore()
			throws InternalErrorException, InstanceNotFoundException,
			NegativeAmountException {
		int operationCount = 25;
		double amount = 25;
		for (int i = 0; i < operationCount; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), amount,
					"Test operation", Calendar.getInstance(), testLeafCategory);
		}
		Block<AccountOperation> accountOperations = accountFacade
				.findAccountOperationsByCategory(testAccount.getAccountId(),
						testLeafCategory.getCategoryId(), 0, operationCount - 1);
		assertTrue(accountOperations.isExistMore());
	}

	/**
	 * This method tests if the Block of AccountOperationInfo returned by the
	 * account facade correctly informs when there are not more account
	 * operations left
	 * 
	 * @throws InternalErrorException
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 */
	@Test
	public void testFindOperationsByCategoryNotExistMore()
			throws InternalErrorException, InstanceNotFoundException,
			NegativeAmountException {
		int operationCount = 25;
		double amount = 25;
		for (int i = 0; i < operationCount; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), amount,
					"Test operation", Calendar.getInstance(), testLeafCategory);
		}
		Long opFound = accountFacade.getOperationsCount(testAccount
				.getAccountId());
		Block<AccountOperation> accountOperations = accountFacade
				.findAccountOperationsByCategory(testAccount.getAccountId(),
						testLeafCategory.getCategoryId(), 0, opFound.intValue());
		assertFalse(accountOperations.isExistMore());
	}

	/**
	 * This method tests if the Block of AccountOperationInfo returned by the
	 * account facade gets filled with the correct amount of account operations
	 * 
	 * @throws InternalErrorException
	 * @throws InstanceNotFoundException
	 * @throws NegativeAmountException
	 */
	@Test
	public void testFindOperationsByCategoryOperationsNumber()
			throws InternalErrorException, InstanceNotFoundException,
			NegativeAmountException {
		// Create some operations
		int operationCount = 25;
		double amount = 25;
		for (int i = 0; i < operationCount; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), amount,
					"Test operation", Calendar.getInstance(), testLeafCategory);
		}
		// Retrieve the global number of operations
		Long opCount = accountFacade.getOperationsCount(testAccount
				.getAccountId());

		Block<AccountOperation> operations = accountFacade
				.findAccountOperations(testAccount.getAccountId(), 0, opCount
						.intValue());

		// Count how many have the desired category
		int opByCategory = 0;
		for (AccountOperation operation : operations.getContents()) {
			if (operation.getCategory().equals(testLeafCategory))
				opByCategory++;
		}

		// Retrieve that number of account operations
		Block<AccountOperation> opByCategoryInfo = accountFacade
				.findAccountOperationsByCategory(testAccount.getAccountId(),
						testLeafCategory.getCategoryId(), 0, opCount.intValue());

		// Check if the number is correct
		assertTrue(opByCategoryInfo.getContents().size() == opByCategory);
	}

	/**
	 * This method tests if the account facade behaves well when trying to
	 * search for account operations for a non-existing category
	 * 
	 * @throws InternalErrorException
	 * @throws InstanceNotFoundException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testFindOperationsByNonExistentCategory()
			throws InternalErrorException, InstanceNotFoundException {
		accountFacade.findAccountOperationsByCategory(testAccount
				.getAccountId(), NON_EXISTENT_CATEGORY_ID, 0, 100);
	}

	@Test
	public void testFindOperationsByParentCategory()
			throws InstanceNotFoundException, NegativeAmountException,
			InternalErrorException {
		int operationsByChildCategory = 0;
		int operationsByParentCategory = 0;

		// Generate some operations with a given category identifier
		int operationCount = 25;
		double amount = 25;
		for (int i = 0; i < operationCount; i++) {
			accountFacade.addToAccount(testAccount.getAccountId(), amount,
					"Test operation", Calendar.getInstance(), testLeafCategory);
		}

		// Get the total operation number
		Long opCount = accountFacade.getOperationsCount(testAccount
				.getAccountId());

		// Retrieve the operations within that category
		Block<AccountOperation> firstSet = accountFacade
				.findAccountOperationsByCategory(testAccount.getAccountId(),
						testLeafCategory.getCategoryId(), 0, opCount.intValue());
		operationsByChildCategory = firstSet.getContents().size();

		// Retrieve the operations within its parent category
		Block<AccountOperation> secondSet = accountFacade
				.findAccountOperationsByCategory(testAccount.getAccountId(),
						testRootCategory.getCategoryId(), 0, opCount.intValue());
		operationsByParentCategory = secondSet.getContents().size();

		// Check that the number in the second set is greater or equal than the
		// first set
		assertTrue("Facade method not implemented",
				operationsByParentCategory >= operationsByChildCategory);
	}

}
