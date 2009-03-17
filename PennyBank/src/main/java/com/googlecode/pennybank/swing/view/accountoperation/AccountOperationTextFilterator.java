package com.googlecode.pennybank.swing.view.accountoperation;

import java.util.List;

import ca.odell.glazedlists.TextFilterator;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.util.MessageManager;

public class AccountOperationTextFilterator implements
		TextFilterator<AccountOperation> {

	public void getFilterStrings(List<String> baseList,
			AccountOperation operation) {
		baseList.add(operation.getComment());
		baseList.add(operation.getCategory() != null ? operation.getCategory()
				.getName() : MessageManager
				.getMessage("Category.Uncategorized"));
	}

}
