/**
 * PingQuery.java
 * 
 * 06/03/2009
 */
package com.googlecode.pennybank.util;

import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;

/**
 * Utility class which tries to make a connection to the database, both hiding
 * the delay in the first access and checking the availability of the database
 * 
 * @author spenap
 * 
 */
public class PingQuery {

	/**
	 * Simple query to initialize the DB It should be called when loading the
	 * MainWindow, so next access to DB will run without any significant delay
	 * 
	 * @return if the query was successful
	 */
	public static boolean Ping() {
		try {
			UserFacadeDelegateFactory.getDelegate().findUsers();
		} catch (InternalErrorException e) {
			return false;
		}
		return true;
	}
}
