package com.googlecode.pennybank.model.account.hibernate;

import java.util.List;

import com.googlecode.pennybank.model.account.dao.AccountDAO;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.util.dao.GenericDAOHibernate;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;

/**
 * Delegate implementing the AccountDAO interface using Hibernate
 * 
 * @author spenap
 */
public class AccountDAOHibernate extends GenericDAOHibernate<Account, Long>
		implements AccountDAO {

	@SuppressWarnings("unchecked")
	public List<Account> findByUser(Long id, int startIndex, int count) {

		return getSession().createQuery(
				"SELECT a " + "FROM Account a "
						+ "WHERE a.user.userId = :userId " + "ORDER BY a.name")
				.setParameter("userId", id).setMaxResults(count)
				.setFirstResult(startIndex).list();
	}

	@Override
	public void remove(Long id) throws InstanceNotFoundException {

		Account theAccount = find(id);

		theAccount.getUser().getAccounts().remove(theAccount);

		super.remove(id);
	}
}
