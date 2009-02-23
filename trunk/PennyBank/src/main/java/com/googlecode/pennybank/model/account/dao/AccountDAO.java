package com.googlecode.pennybank.model.account.dao;

import java.util.List;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.util.dao.GenericDao;

/**
 * DAO Interface for accessing the account entity
 *
 * @author spenap
 */
public interface AccountDAO extends GenericDao<Account, Long> {

	/**
     * Retrieves an account list for a given user identifier
     *
	 * @param id The user identifier
	 * @param startIndex The start index to retrieve the accounts
	 * @param count The number of accounts to retrieve
	 * @return the list of accounts
	 */
	public List<Account> findByUser(Long id, int startIndex,
			int count);

}
