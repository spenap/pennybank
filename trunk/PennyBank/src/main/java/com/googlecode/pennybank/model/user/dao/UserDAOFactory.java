package com.googlecode.pennybank.model.user.dao;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.util.configuration.ConfigurationManager;

/**
 * A factory to retrieve an UserDAO interface implementation
 *
 * @author spenap
 */
public class UserDAOFactory {

	private final static String DAO_CLASS_NAME = "UserDAOFactory/daoClassName";

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
     * Method returning an UserDAO implementation
     * @return A delegate implementing the UserDAO interface
     * @throws InternalErrorException If an unexpected error happened
     */
    public static UserDAO getDelegate() throws InternalErrorException {

		try {
			return (UserDAO) daoClass.newInstance();
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}
}
