package com.googlecode.pennybank.model.account.dao;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.util.configuration.ConfigurationManager;

/**
 * Factory to retrieve an AccountDAO implementation
 * 
 * @author spenap
 */
public class AccountDAOFactory {

	private final static String DAO_CLASS_NAME = "AccountDAOFactory/daoClassName";

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

    /**
     * Method to retrieve a delegate implementing the AccountDAO interface
     * @return A delegate implementing the AccountDAO interface
     * @throws InternalErrorException if there is any error
     */
    public static AccountDAO getDelegate()
			throws InternalErrorException {

		try {
			return (AccountDAO) daoClass.newInstance();
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}
}
