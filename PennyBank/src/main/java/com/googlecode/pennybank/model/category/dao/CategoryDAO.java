package com.googlecode.pennybank.model.category.dao;

import java.util.List;

import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.dao.GenericDao;

public interface CategoryDAO extends GenericDao<Category, Long>{
	
	List<Category> findByOperation(Long accountOpId);
	
	List<Category> findAll();

}
