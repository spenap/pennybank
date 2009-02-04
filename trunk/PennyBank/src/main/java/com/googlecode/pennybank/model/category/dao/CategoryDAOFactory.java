package com.googlecode.pennybank.model.category.dao;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.util.configuration.ConfigurationManager;

public class CategoryDAOFactory {

	private final static String DAO_CLASS_NAME = "CategoryDAOFactory/daoClassName";

	@SuppressWarnings("unchecked")
	private final static Class daoClass = getDaoClass();

	@SuppressWarnings("unchecked")
	private final static Class getDaoClass() {

		Class theClass = null;
		try {
			String daoClassName = ConfigurationManager
					.getParameter(DAO_CLASS_NAME);

			theClass = Class.forName(daoClassName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return theClass;
	}

	public static CategoryDAO getDelegate() throws InternalErrorException {

		try {
			return (CategoryDAO) daoClass.newInstance();
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}
}
