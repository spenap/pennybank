package com.googlecode.pennybank.model.accountoperation.dao;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.util.configuration.ConfigurationManager;

/**
 * A Factory to retrieve an AccountOperationDAO implementation
 *
 * @author spenap
 */
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

    /**
     * Method returning a delegate implementing the AccountOperationDAO
     * @return A delegate which implements AccountOperationDAO
     * @throws InternalErrorException If an unexpected error happens
     */
    public static AccountOperationDAO getDAO()
			throws InternalErrorException {

		try {
			return (AccountOperationDAO) daoClass.newInstance();
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}
}
