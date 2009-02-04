package com.googlecode.pennybank.model.account.dao;

import java.util.List;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.util.dao.GenericDao;

public interface AccountDAO extends GenericDao<Account, Long> {

	/**
	 * @param id The userId
	 * @param startIndex The startIndex
	 * @param count The count
	 * @return the list of accounts
	 */
	public List<Account> findByUser(Long id, int startIndex,
			int count);

}
