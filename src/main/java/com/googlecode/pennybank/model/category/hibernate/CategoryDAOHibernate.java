package com.googlecode.pennybank.model.category.hibernate;

import java.util.List;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.category.dao.CategoryDAO;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.dao.GenericDAOHibernate;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;

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

	@Override
	public void remove(Long id) throws InstanceNotFoundException {
		Category theCategory = find(id);

		// Remove itself from parent category
		if (theCategory.getParentCategory() != null) {
			theCategory.getParentCategory().getChildCategories().remove(
					theCategory);
		}

		// Remove child categories
		for (Category childCategory : theCategory.getChildCategories()) {
			childCategory.setParentCategory(null);
		}

		for (AccountOperation accountOperation : findOperations(id)) {
			accountOperation.setCategory(null);
		}

		super.remove(theCategory.getCategoryId());
	}

	@SuppressWarnings("unchecked")
	private List<AccountOperation> findOperations(Long id) {
		return getSession().createQuery(
				"SELECT a " + "FROM AccountOperation a "
						+ "WHERE a.category.categoryId = :categoryId "
						+ "ORDER BY a.date").setParameter("categoryId", id)
				.list();
	}
}
