package com.googlecode.pennybank.model.accountfacade.delegate;

import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.util.configuration.ConfigurationManager;

public class AccountFacadeDelegateFactory {

	private final static String FACADE_CLASS_NAME = "AccountFacadeDelegateFactory/delegateClassName";

	@SuppressWarnings("unchecked")
	private final static Class delegateClass = getDelegateClass();

	@SuppressWarnings("unchecked")
	private final static Class getDelegateClass() {
		Class theClass = null;
		try {
			String delegateClassName = ConfigurationManager
					.getParameter(FACADE_CLASS_NAME);

			theClass = Class.forName(delegateClassName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return theClass;
	}

	public static AccountFacadeDelegate getDelegate()
			throws InternalErrorException {
		try {
			return (AccountFacadeDelegate) delegateClass.newInstance();
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}
}
