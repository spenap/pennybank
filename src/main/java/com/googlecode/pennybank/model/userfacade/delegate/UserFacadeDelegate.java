package com.googlecode.pennybank.model.userfacade.delegate;

import java.util.List;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;

/**
 * The UserFacade interface
 * 
 * @author spenap
 */
public interface UserFacadeDelegate {

	/**
	 * Creates an user with the specified parameters
	 * 
	 * @param user
	 *            The user to be created
	 * @return The created user
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public User createUser(User user) throws InternalErrorException;

	/**
	 * Deletes a user with the user identifier given
	 * 
	 * @param userId
	 *            The user identifier to look for
	 * @throws InstanceNotFoundException
	 *             if the user was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public void deleteUser(Long userId) throws InstanceNotFoundException,
			InternalErrorException;

	/**
	 * Retrieves all the users in the database
	 * 
	 * @return A list of users
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public List<User> findUsers() throws InternalErrorException;

	/**
	 * Searches for a user with the specified user identifier
	 * 
	 * @param userId
	 *            The user identifier to look for
	 * @return The User with the given user identifier
	 * @throws InstanceNotFoundException
	 *             if the user was not found
	 * @throws InternalErrorException
	 *             Exception encapsulating a non logical error
	 */
	public User find(Long userId) throws InstanceNotFoundException,
			InternalErrorException;

	/**
	 * Updates an user
	 * 
	 * @param theUser
	 *            the user to update
	 * @return the user up to date
	 * @throws InstanceNotFoundException
	 *             if the user was not found
	 * @throws InternalErrorException
	 *             if an unexpected error happened
	 */
	public User updateUser(User theUser) throws InstanceNotFoundException,
			InternalErrorException;
}
