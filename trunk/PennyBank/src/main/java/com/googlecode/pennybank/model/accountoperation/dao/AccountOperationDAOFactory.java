package com.googlecode.pennybank.model.accountoperation.dao;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.util.configuration.ConfigurationManager;

public class AccountOperationDAOFactory {

	private final static String DAO_CLASS_NAME = "AccountOperationDAOFactory/daoClassName";

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

	public static AccountOperationDAO getDelegate()
			throws InternalErrorException {

		try {
			return (AccountOperationDAO) daoClass.newInstance();
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}
}
