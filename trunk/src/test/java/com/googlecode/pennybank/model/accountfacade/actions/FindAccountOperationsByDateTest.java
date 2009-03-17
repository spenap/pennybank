/**
 * FindAccountOperationsByDateTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Random;

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

/**
 * Class which tests the behavior of the FindAccountOperationsByDate method in
 * the account facade
 * 
 * @author spenap
 */
public class FindAccountOperationsByDateTest {

	private static AccountFacadeDelegate accountFacade;
	private static UserFacadeDelegate userFacade;
	private static User theUser;
	private static Account theAccount;
	private static int operationsMade;
	private static double balance = 100;
	private static Calendar startDate;
	private static Calendar endDate;

	/**
	 * Sets up the objects needed to do the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		accountFacade = AccountFacadeDelegateFactory.getDelegate();
		userFacade = UserFacadeDelegateFactory.getDelegate();

		theUser = new User("Test user");
		theUser = userFacade.createUser(theUser);

		theAccount = new Account(theUser, balance, "Test account");
		theAccount = accountFacade.createAccount(theAccount);

		Random random = new Random();
		operationsMade = random.nextInt(30);
		startDate = Calendar.getInstance();
		startDate.set(2003, 1, 11);
		endDate = Calendar.getInstance();
		endDate.setTime(startDate.getTime());

		for (int i = 0; i < operationsMade; i++) {
			endDate.add(Calendar.DAY_OF_MONTH, i);
			accountFacade.addToAccount(theAccount.getAccountId(), 100,
					"Test addition " + i, endDate, null);
		}

	}

	/**
	 * Clears the objects set to do the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		accountFacade.deleteAccount(theAccount.getAccountId());
		userFacade.deleteUser(theUser.getUserId());
	}

	/**
	 * Tests if the facade method retrieves the account operations between two
	 * selected dates
	 * 
	 * @throws InternalErrorException
	 * @throws InstanceNotFoundException
	 */
	@Test
	public void testFindAccountOperationsByDate()
			throws InternalErrorException, InstanceNotFoundException {
		Long opCount = accountFacade.getOperationsCount(theAccount
				.getAccountId());

		Block<AccountOperation> accountOperations = accountFacade
				.findAccountOperationsByDate(theAccount.getAccountId(),
						startDate, endDate, 0, opCount.intValue());

		assertEquals(operationsMade, accountOperations.getContents().size());
	}

}
