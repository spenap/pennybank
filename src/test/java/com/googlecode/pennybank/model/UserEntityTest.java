/**
 * FindAccountOperationsByCategoryTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;

/**
 * @author spenap
 * 
 */
public class UserEntityTest {

	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static final double INITIAL_BALANCE = 12345.678;
	private static final String ACCOUNT_NAME = "Test Account";
	private static User testUser;
	private static List<Account> expectedAccounts;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();
		testUser = new User("Test User");
		testUser = userFacade.createUser(testUser);
		expectedAccounts = new ArrayList<Account>();
		for (int i = 0; i < 5; i++) {
			Account testAccount = new Account(testUser, INITIAL_BALANCE,
					ACCOUNT_NAME);
			testAccount = accountFacade.createAccount(testAccount);
			expectedAccounts.add(testAccount);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		userFacade.deleteUser(testUser.getUserId());
	}

	@Test
	public void testUserAccounts() {
		assertTrue(testUser.getAccounts().containsAll(expectedAccounts));
	}

	// /**
	// * Method which tests the account facade behavior when searching for an
	// * existent account of an existent user
	// *
	// * @throws InternalErrorException
	// * @throws InstanceNotFoundException
	// */
	// @Test
	// public void testFindExistentAccountExistentUser()
	// throws InstanceNotFoundException, InternalErrorException {
	// Block<Account> theAccounts = accountFacade.findAccountByUserId(testUser
	// .getUserId(), 0, 100);
	// assertTrue(theAccounts.getContents().contains(testAccount1));
	//
	// }
	//
	// /**
	// * Method which tests if the account facade retrieves all the actual
	// * accounts of an user
	// *
	// * @throws InternalErrorException
	// * @throws InstanceNotFoundException
	// */
	// public void testFindMultipleAccounts() throws InstanceNotFoundException,
	// InternalErrorException {
	//
	// Block<Account> theAccounts = accountFacade.findAccountByUserId(testUser
	// .getUserId(), 0, 100);
	// List<Account> expectedAccounts = new ArrayList<Account>();
	// expectedAccounts.add(testAccount1);
	// expectedAccounts.add(testAccount2);
	// expectedAccounts.add(testAccount3);
	// expectedAccounts.add(testAccount4);
	// assertTrue(theAccounts.getContents().containsAll(expectedAccounts));
	// }
	//
	// /**
	// * Method which tests the if the account facade retrieves the exact number
	// * of account operations asked
	// *
	// * @throws InternalErrorException
	// * @throws InstanceNotFoundException
	// */
	// @Test
	// public void testFindAccountsContentSize() throws
	// InstanceNotFoundException,
	// InternalErrorException {
	// Block<Account> theAccounts = accountFacade.findAccountByUserId(testUser
	// .getUserId(), 0, 1);
	// assertTrue(theAccounts.getContents().size() == 1);
	//
	// }
	//
	// /**
	// * Method which tests the account facade behavior when searching for
	// * accounts giving a count number less than the actual one
	// *
	// * @throws InternalErrorException
	// * @throws InstanceNotFoundException
	// */
	// @Test
	// public void testFindAccountsExistMore() throws InstanceNotFoundException,
	// InternalErrorException {
	// Block<Account> theAccounts = accountFacade.findAccountByUserId(testUser
	// .getUserId(), 0, 1);
	// assertTrue(theAccounts.isExistMore());
	// }
	//
	// /**
	// * Method which tests the account facade behavior when searching for
	// * accounts of a non-existent user
	// *
	// * @throws InstanceNotFoundException
	// * @throws InternalErrorException
	// */
	// @Test(expected = InstanceNotFoundException.class)
	// public void testFindExistentAccountNonExistentUser()
	// throws InstanceNotFoundException, InternalErrorException {
	// accountFacade.findAccountByUserId(NON_EXISTENT_USER_ID, 0, 100);
	// }
}
