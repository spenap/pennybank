/**
 * 
 */
package com.googlecode.pennybank.swing.view.category;

import ca.odell.glazedlists.gui.WritableTableFormat;

import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class CategoriesTableFormat implements WritableTableFormat<Category> {

	private String[] columnNames = {
			MessageManager.getMessage("CategoryWindow.CategoryName"),
			MessageManager.getMessage("CategoryWindow.ParentCategory") };

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	public Object getColumnValue(Category category, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return category.getName();
		case 1:
			if (category.getParentCategory() != null)
				return category.getParentCategory().getName();
			else
				return MessageManager.getMessage("Category.Uncategorized");
		default:
			return null;
		}
	}

	public boolean isEditable(Category category, int columnIndex) {
		return true;
	}

	public Category setColumnValue(Category category, Object value,
			int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (value instanceof String)
				category.setName((String) value);
			break;
		case 1:
			if (value instanceof Category)
				category.setParentCategory((Category) value);
			break;
		default:
			break;
		}
		return category;
	}
}
