/**
 * FindUserTest.java
 * 
 * 28/02/2009
 */
package com.googlecode.pennybank.model.userfacade.actions;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;

/**
 * Test case for checking the behavior of the FindUser method in the UserFacade
 * 
 * @author spenap
 */
public class FindUserTest {

	private static final long NON_EXISTENT_USER_ID = -1;
	private static UserFacadeDelegate userFacade;
	private static User user;

	/**
	 * Creates the user to be used in the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		user = new User("Test User");
		user = userFacade.createUser(user);
	}

	/**
	 * Removes the user created for the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		userFacade.deleteUser(user.getUserId());
	}

	/**
	 * Method which tests if the user facade behaves well when searching for an
	 * user
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testFindExistentUser() throws InstanceNotFoundException,
			InternalErrorException {
		User theUser = userFacade.find(user.getUserId());
		assertEquals(user, theUser);
	}

	/**
	 * Method which tests if the user facade behaves well when searching for a
	 * non-existent user
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentUser() throws InstanceNotFoundException,
			InternalErrorException {
		userFacade.find(NON_EXISTENT_USER_ID);
	}
}
