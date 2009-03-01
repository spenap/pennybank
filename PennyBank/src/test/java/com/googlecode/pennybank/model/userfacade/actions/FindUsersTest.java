/**
 * FindUsersTest.java
 * 
 * 28/02/2009
 */
package com.googlecode.pennybank.model.userfacade.actions;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;

/**
 * Test case for checking the behavior of the FindUsers method in the UserFacade
 * 
 * @author spenap
 */
public class FindUsersTest {

	private static UserFacadeDelegate userFacade;
	private static List<User> toDelete;
	private static int userCount;

	/**
	 * This method creates all the users needed for the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		toDelete = new ArrayList<User>();
		Random random = new Random();
		userCount = random.nextInt(30);
		for (int i = 0; i < userCount; i++) {
			User user = new User("User " + i);
			user = userFacade.createUser(user);
			toDelete.add(user);
		}
	}

	/**
	 * This method clears up all the users previously created
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		for (User user : toDelete) {
			userFacade.deleteUser(user.getUserId());
		}
	}

	/**
	 * Method which tests if the user facade behaves well when searching all the
	 * users
	 * 
	 * @throws InternalErrorException
	 */
	@Test
	public void testFindUsers() throws InternalErrorException {
		List<User> usersFound = userFacade.findUsers();
		assertTrue(toDelete.containsAll(usersFound));
	}

}
