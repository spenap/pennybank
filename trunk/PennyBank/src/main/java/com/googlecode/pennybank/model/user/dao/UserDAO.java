package com.googlecode.pennybank.model.user.dao;

import java.util.List;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.util.dao.GenericDao;

public interface UserDAO extends GenericDao<User, Long> {
	
	List<User> findAll();
	
}
