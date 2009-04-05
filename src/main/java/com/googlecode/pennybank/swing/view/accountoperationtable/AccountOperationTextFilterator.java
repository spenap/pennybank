package com.googlecode.pennybank.swing.view.accountoperationtable;

import java.util.List;

import ca.odell.glazedlists.TextFilterator;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.util.MessageManager;

public class AccountOperationTextFilterator implements
		TextFilterator<AccountOperation> {

	public void getFilterStrings(List<String> baseList,
			AccountOperation operation) {
		switch (AccountOperationTableFilter.getSearchMode()) {
		case All:
			baseList.add(operation.getComment());
			baseList.add(operation.getCategory() != null ? operation
					.getCategory().getName() : MessageManager
					.getMessage("Category.Uncategorized"));
			break;
		case Category:
			baseList.add(operation.getCategory() != null ? operation
					.getCategory().getName() : MessageManager
					.getMessage("Category.Uncategorized"));
			break;
		case Comments:
			baseList.add(operation.getComment());
			break;
		default:
			break;
		}
	}
}
