package com.googlecode.pennybank.model.account.hibernate;

import java.util.List;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.util.dao.GenericDAOHibernate;

public class AccountDAOHibernate extends GenericDAOHibernate<Account, Long>
		implements AccountDAO {

	@SuppressWarnings("unchecked")
	public List<Account> findByUser(Long id, int startIndex, int count) {

		return getSession().createQuery(
				"SELECT a " + "FROM Account a "
						+ "WHERE a.user.userId = :usreId " + "ORDER BY a.name")
				.setParameter("usreId", id).list();
	}
}
