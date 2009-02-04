package com.googlecode.pennybank.model.accountoperation.dao;

import java.util.Calendar;
import java.util.List;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.dao.GenericDao;

public interface AccountOperationDAO extends GenericDao<AccountOperation, Long> {

	List<AccountOperation> findByAccount(Long accountId, int startIndex,
			int count);

	List<AccountOperation> findByOperation(Long accountId,
			AccountOperation.Type type, int startIndex, int count);

	List<AccountOperation> findByDate(Long accountId, Calendar startDate,
			Calendar endDate, int startIndex, int count);

	List<AccountOperation> findByCategory(Long accountId, Long categoryId,
			int startIndex, int count);

	Long getOperationsCount(Long accountId);
}
