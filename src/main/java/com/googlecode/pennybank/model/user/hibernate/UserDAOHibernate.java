package com.googlecode.pennybank.model.user.hibernate;

import java.util.List;

import com.googlecode.pennybank.model.user.dao.UserDAO;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.util.dao.GenericDAOHibernate;

/**
 * An UserDAO interface implementation, using Hibernate
 *
 * @author spenap
 */
public class UserDAOHibernate extends GenericDAOHibernate<User, Long> implements
		UserDAO {

    @SuppressWarnings("unchecked")
	public List<User> findAll() {

		return getSession().createQuery("SELECT u FROM User u ORDER BY u.name")
				.list();

	}

}
