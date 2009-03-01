package com.googlecode.pennybank.model.category.dao;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.util.configuration.ConfigurationManager;

/**
 * A Factory to retrieve a CategoryDAO implementation
 * 
 * @author spenap
 */
public class CategoryDAOFactory {

    private final static String DAO_CLASS_NAME =
            "CategoryDAOFactory/daoClassName";
    @SuppressWarnings("unchecked")
    private final static Class daoClass = getDaoClass();

    @SuppressWarnings("unchecked")
    private final static Class getDaoClass() {

        Class theClass = null;
        try {
            String daoClassName =
                    ConfigurationManager.getParameter(DAO_CLASS_NAME);

            theClass = Class.forName(daoClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return theClass;
    }

    /**
     * A delegate implementing the CategoryDAO interface
     * 
     * @return a CategoryDAO implementation
     * @throws InternalErrorException If an unexpected error happened
     */
    public static CategoryDAO getDAO()
            throws InternalErrorException {

        try {
            return (CategoryDAO) daoClass.newInstance();
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }
}
