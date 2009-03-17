package com.googlecode.pennybank.model.accountfacade.delegate;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.util.configuration.ConfigurationManager;

/**
 * Factory to retrieve a delegate implementing the AccountFacade interface
 * @author spenap
 */
public class AccountFacadeDelegateFactory {

    private final static String FACADE_CLASS_NAME =
            "AccountFacadeDelegateFactory/delegateClassName";
    @SuppressWarnings("unchecked")
    private final static Class delegateClass = getDelegateClass();

    @SuppressWarnings("unchecked")
    private final static Class getDelegateClass() {
        Class theClass = null;
        try {
            String delegateClassName =
                    ConfigurationManager.getParameter(FACADE_CLASS_NAME);

            theClass = Class.forName(delegateClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return theClass;
    }

    /**
     * Method to retrieve an delegate implementing the Account Facade interface
     * @return A delegate implementing the Account Facade interface
     * @throws InternalErrorException if an unexpected error happens
     */
    public static AccountFacadeDelegate getDelegate()
            throws InternalErrorException {
        try {
            return (AccountFacadeDelegate) delegateClass.newInstance();
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }
}
