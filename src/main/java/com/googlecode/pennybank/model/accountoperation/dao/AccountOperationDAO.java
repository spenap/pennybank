package com.googlecode.pennybank.model.accountoperation.dao;

import java.util.Calendar;
import java.util.List;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.dao.GenericDao;

/**
 * The AccountOperationDAO interface
 *
 * @author spenap
 */
public interface AccountOperationDAO extends GenericDao<AccountOperation, Long> {

    /**
     * Finds the account operations given an account identifier. It allows using
     * the Page-By-Page iterator pattern, so it uses an index to start retrieving 
     * operations, an a count of operations to be retrieved at once
     *
     * @param accountId The account identifier to search within
     * @param startIndex The index to start searching
     * @param count The number of operations to retrieve
     * @return An AccountOperation list
     */
    List<AccountOperation> findByAccount(Long accountId, int startIndex,
            int count);

    /**
     * Finds "count" account operations given an operation type, starting
     * at the given startIndex.
     *
     * @param accountId The account identifier to search within
     * @param type The operation type
     * @param startIndex The index to start searching
     * @param count The number of operations to retrieve
     * @return An AccountOperation list
     */
    List<AccountOperation> findByOperation(Long accountId,
            AccountOperation.Type type, int startIndex, int count);

    /**
     * Finds "count" account operations between two given dates, starting
     * at a given startIndex
     *
     * @param accountId The account identifier to search within
     * @param startDate The date to start looking for operations
     * @param endDate The date to end looking for operations
     * @param startIndex The start index to start searching
     * @param count The number of operations to retrieve
     * @return An account operation list
     */
    List<AccountOperation> findByDate(Long accountId, Calendar startDate,
            Calendar endDate, int startIndex, int count);

    /**
     * Finds "count" account operations for a given category, starting at
     * a given "startIndex"
     *
     * @param accountId The account identifier to search within
     * @param categoryId The category identifier
     * @param startIndex The index to start searching
     * @param count The number of operations to retrieve
     * @return An account operation list
     */
    List<AccountOperation> findByCategory(Long accountId, Long categoryId,
            int startIndex, int count);

    /**
     * Retrieves the number of account operations
     *
     * @param accountId
     * @return
     */
    Long getOperationsCount(Long accountId);
}
