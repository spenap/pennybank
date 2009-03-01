package com.googlecode.pennybank.model.accountoperation.hibernate;

import java.util.Calendar;
import java.util.List;

import com.googlecode.pennybank.model.accountoperation.dao.AccountOperationDAO;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.util.dao.GenericDAOHibernate;

/**
 * Class implementing the AccountOperationDAO interface, using Hibernate
 * 
 * @author spenap
 */
public class AccountOperationDAOHibernate extends
		GenericDAOHibernate<AccountOperation, Long> implements
		AccountOperationDAO {

	@SuppressWarnings("unchecked")
	public List<AccountOperation> findByAccount(Long accountId, int startIndex,
			int count) {

		return getSession().createQuery(
				"SELECT a " + "FROM AccountOperation a "
						+ "WHERE a.account.accountId = :accountId "
						+ "ORDER BY a.date").setParameter("accountId",
				accountId).setFirstResult(startIndex).setMaxResults(count)
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<AccountOperation> findByCategory(Long accountId,
			Long categoryId, int startIndex, int count) {
		return getSession()
				.createQuery(
						"SELECT a "
								+ "FROM AccountOperation a "
								+ "WHERE a.account.accountId = :accountId AND a.category.categoryId = :categoryId "
								+ "ORDER BY a.date").setParameter("accountId",
						accountId).setParameter("categoryId", categoryId)
				.setFirstResult(startIndex).setMaxResults(count).list();
	}

	@SuppressWarnings("unchecked")
	public List<AccountOperation> findByDate(Long accountId,
			Calendar startDate, Calendar endDate, int startIndex, int count) {
		return getSession()
				.createQuery(
						"SELECT a "
								+ "FROM AccountOperation a "
								+ "WHERE a.date < :endDate "
								+ "AND a.date > :startDate AND a.account.accountId = :accountId "
								+ "ORDER BY a.date").setParameter("endDate",
						endDate).setParameter("startDate", startDate)
				.setParameter("accountId", accountId)
				.setFirstResult(startIndex).setMaxResults(count).list();
	}

	@SuppressWarnings("unchecked")
	public List<AccountOperation> findByOperation(Long accountId, Type type,
			int startIndex, int count) {
		return getSession().createQuery(
				"SELECT a " + "FROM AccountOperation a "
						+ "WHERE a.account.accountId = :accountId "
						+ "AND a.type = :type " + "ORDER BY a.date")
				.setParameter("accountId", accountId)
				.setParameter("type", type).setFirstResult(startIndex)
				.setMaxResults(count).list();
	}

	public Long getOperationsCount(Long accountId) {

		return (Long) getSession().createQuery(
				"SELECT COUNT (a) " + "FROM AccountOperation a "
						+ "WHERE a.account.accountId = :accountId")
				.setParameter("accountId", accountId).uniqueResult();
	}
}
