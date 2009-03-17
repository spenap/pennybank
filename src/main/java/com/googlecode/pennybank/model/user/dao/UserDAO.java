package com.googlecode.pennybank.model.user.dao;

import java.util.List;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.util.dao.GenericDao;

/**
 * UserDAO interface
 *
 * @author spenap
 */
public interface UserDAO extends GenericDao<User, Long> {
	
    /**
     * Finds all users
     *
     * @return A list containing all users
     */
    List<User> findAll();
	
}
