/**
 * DeleteUserTest.java
 * 
 * 28/02/2009
 */
package com.googlecode.pennybank.model.userfacade.actions;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;

/**
 * Tests which check the behavior of the DeleteUser method in the UserFacade
 * 
 * @author spenap
 */
public class DeleteUserTest {

	private static final long NON_EXISTENT_USER_ID = -1;
	private static UserFacadeDelegate userFacade;
	private static User theUser;

	/**
	 * Creates all the entities needed for the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();

		theUser = new User("Test User");
		theUser = userFacade.createUser(theUser);
	}

	/**
	 * Method which tests if the user facade behaves well when asked to delete
	 * an existent user
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testDeleteExistentUser() throws InstanceNotFoundException,
			InternalErrorException {
		userFacade.deleteUser(theUser.getUserId());
		boolean exceptionCatched = false;
		try {
			userFacade.find(theUser.getUserId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

	/**
	 * Method which tests if the user facade behaves well when asked to delete a
	 * non existent user
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testDeleteNonExistentUser() throws InstanceNotFoundException,
			InternalErrorException {
		userFacade.deleteUser(NON_EXISTENT_USER_ID);
	}
}
