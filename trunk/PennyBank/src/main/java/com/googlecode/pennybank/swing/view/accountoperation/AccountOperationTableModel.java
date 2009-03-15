/**
 * 
 */
package com.googlecode.pennybank.swing.view.accountoperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
@SuppressWarnings("serial")
public class AccountOperationTableModel extends AbstractTableModel {

	private String[] columnNames = {
			MessageManager.getMessage("AccountOperationTable.Type"),
			MessageManager.getMessage("AccountOperationTable.Amount"),
			MessageManager.getMessage("AccountOperationTable.Date"),
			MessageManager.getMessage("AccountOperationTable.Comment"),
			MessageManager.getMessage("AccountOperationTable.Category") };
	private List<AccountOperation> accountOperations = new ArrayList<AccountOperation>();

	public AccountOperationTableModel() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return accountOperations.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		// Get the corresponding operation
		AccountOperation currentRowOperation = accountOperations.get(rowIndex);

		// Get the selected field
		switch (columnIndex) {
		case 0:
			return MessageManager.getMessage(currentRowOperation.getType()
					.toString());
		case 1:
			return currentRowOperation.getAmount();
		case 2:
			return currentRowOperation.getDate().getTime();
		case 3:
			return currentRowOperation.getComment();
		case 4:
			String categoryText = null;
			if (currentRowOperation.getCategory() != null)
				categoryText = currentRowOperation.getCategory().getName();
			else
				categoryText = MessageManager
						.getMessage("Category.Uncategorized");
			return categoryText;
		default:
			return null;
		}
	}

	@Override
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
			return null;
		}
	}

	public void setContent(List<AccountOperation> contents) {
		this.accountOperations = contents;
		fireTableDataChanged();
	}
}
