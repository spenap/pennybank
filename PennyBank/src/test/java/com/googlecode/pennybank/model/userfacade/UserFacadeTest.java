package com.googlecode.pennybank.model.userfacade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class UserFacadeTest {

	private static UserFacadeDelegate userFacade;
	private final long NON_EXISTENT_USER_ID = -1;

	@BeforeClass
	public static void setUpBeforeClass() {

		try {
			userFacade = UserFacadeDelegateFactory.getDelegate();
		} catch (InternalErrorException e) {

			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() {

		try {
			userFacade = UserFacadeDelegateFactory.getDelegate();
			List<User> users = userFacade.findUsers();
			for (User user : users) {
				userFacade.deleteUser(user.getUserId());
			}

		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateUser() throws InstanceNotFoundException,
			InternalErrorException {

		User theUser = new User("Usuario 1");
		theUser = userFacade.createUser(theUser);
		User otherUser = userFacade.find(theUser.getUserId());

		assertEquals(theUser, otherUser);
	}

	@Test
	public void testDeleteExistentUser() throws InternalErrorException,
			InstanceNotFoundException {

		boolean exceptionCatched = false;
		User theUser = new User("nuevo usuario");
		theUser = userFacade.createUser(theUser);
		userFacade.deleteUser(theUser.getUserId());
		try {
			userFacade.find(theUser.getUserId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}

		assertTrue(exceptionCatched);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testDeleteNonExistentUser() throws InstanceNotFoundException,
			InternalErrorException {

		userFacade.deleteUser(NON_EXISTENT_USER_ID);
	}

	@Test
	public void testFindUsers() throws InternalErrorException {

		User anUser = new User("an User");
		User anotherUser = new User("another User");
		List<User> firstUserList = new ArrayList<User>();
		firstUserList.add(anUser);
		firstUserList.add(anotherUser);
		for (User user : firstUserList) {
			userFacade.createUser(user);
		}

		List<User> userList = userFacade.findUsers();

		assertTrue(userList.containsAll(firstUserList));
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentUser() throws InstanceNotFoundException,
			InternalErrorException {

		userFacade.find(NON_EXISTENT_USER_ID);
	}

}
