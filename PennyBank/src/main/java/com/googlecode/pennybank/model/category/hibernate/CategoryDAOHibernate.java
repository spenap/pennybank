package com.googlecode.pennybank.model.category.hibernate;

import java.util.List;

import com.googlecode.pennybank.model.category.dao.CategoryDAO;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.dao.GenericDAOHibernate;

/**
 * Class implementing the CategoryDAO interface, using Hibernate
 * 
 * @author spenap
 */
public class CategoryDAOHibernate extends GenericDAOHibernate<Category, Long>
		implements CategoryDAO {

	@SuppressWarnings("unchecked")
	public List<Category> findAll() {
		return getSession().createQuery(
				"SELECT c FROM Category c ORDER BY c.name").list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> findByOperation(Long accountOpId) {
		return getSession().createQuery(
				"SELECT c "
						+ "FROM Category c INNER JOIN c.accountOperations a "
						+ "WHERE a.operationId = :accountOpId "
						+ "ORDER BY c.name").setParameter("accountOpId",
				accountOpId).list();
	}

}
