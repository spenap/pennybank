/**
 * CreateUserTest.java
 * 
 * 28/02/2009
 */
package com.googlecode.pennybank.model.userfacade.actions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;

/**
 * Tests to check the behavior of the CreateUser method in the UserFacade
 * 
 * @author spenap
 */
public class CreateUserTest {

	private static UserFacadeDelegate userFacade;
	private static List<User> toDelete;

	/**
	 * Creates all the entities needed for the test
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		toDelete = new ArrayList<User>();
	}

	/**
	 * Removes all the entities created in the test
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
	 * Method which tests the creation of an user
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testCreateUser() throws InstanceNotFoundException,
			InternalErrorException {

		User theUser = new User("Usuario 1");
		theUser = userFacade.createUser(theUser);
		User otherUser = userFacade.find(theUser.getUserId());

		assertEquals(theUser, otherUser);
		toDelete.add(theUser);
	}
}
