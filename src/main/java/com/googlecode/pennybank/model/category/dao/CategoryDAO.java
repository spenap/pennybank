package com.googlecode.pennybank.model.category.dao;

import java.util.List;

import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.dao.GenericDao;

/**
 * CategoryDAO interface
 * 
 * @author spenap
 */
public interface CategoryDAO extends GenericDao<Category, Long> {

	/**
	 * Finds all the categories
	 * 
	 * @return A category list with all the categories
	 */
	List<Category> findAll();
}
