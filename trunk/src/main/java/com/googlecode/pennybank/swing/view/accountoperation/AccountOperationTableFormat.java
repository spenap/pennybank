/**
 * 
 */
package com.googlecode.pennybank.swing.view.accountoperation;

import java.util.Comparator;
import java.util.Date;

import ca.odell.glazedlists.gui.AdvancedTableFormat;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class AccountOperationTableFormat implements
		AdvancedTableFormat<AccountOperation> {
	private String[] columnNames = {
			MessageManager.getMessage("AccountOperationTable.Type"),
			MessageManager.getMessage("AccountOperationTable.Amount"),
			MessageManager.getMessage("AccountOperationTable.Date"),
			MessageManager.getMessage("AccountOperationTable.Comment"),
			MessageManager.getMessage("AccountOperationTable.Category") };

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Object getColumnValue(AccountOperation operation, int columnIndex) {

		switch (columnIndex) {
		case 0:
			return MessageManager.getMessage(operation.getType().toString());
		case 1:
			return operation.getAmount();
		case 2:
			return operation.getDate().getTime();
		case 3:
			return operation.getComment();
		case 4:
			String categoryText = null;
			if (operation.getCategory() != null)
				categoryText = operation.getCategory().getName();
			else
				categoryText = MessageManager
						.getMessage("Category.Uncategorized");
			return categoryText;
		default:
			throw new IllegalStateException();
		}
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Double.class;
		case 2:
			return Date.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		default:
			throw new IllegalStateException();
		}
	}

	public Comparator<?> getColumnComparator(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.CASE_INSENSITIVE_ORDER;
		case 1:
			return new Comparator<Double>() {

				public int compare(Double o1, Double o2) {
					return o1.compareTo(o2);
				}

			};
		case 2:
			return new Comparator<Date>() {

				public int compare(Date o1, Date o2) {
					return o1.compareTo(o2);
				}

			};
		case 3:
			return String.CASE_INSENSITIVE_ORDER;
		case 4:
			return String.CASE_INSENSITIVE_ORDER;
		default:
			throw new IllegalStateException();
		}
	}
}
