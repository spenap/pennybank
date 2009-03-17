/**
 * 
 */
package com.googlecode.pennybank.swing.view.util;

import java.awt.Component;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author spenap
 * 
 */
@SuppressWarnings("serial")
public class AccountOperationTableRenderer extends DefaultTableCellRenderer {

	private DateFormat dateFormat;
	private NumberFormat numberFormat;

	public AccountOperationTableRenderer() {
		dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
		numberFormat = NumberFormat.getCurrencyInstance();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		String formattedValue = null;
		if (value instanceof Date) {
			Date date = (Date) value;
			formattedValue = dateFormat.format(date);
		} else {
			Double amount = (Double) value;
			formattedValue = numberFormat.format(amount);
		}
		JLabel label = new JLabel(formattedValue);

		if (isSelected) {
			label.setBackground(table.getSelectionBackground());
			label.setOpaque(true);
			label.setForeground(table.getSelectionForeground());
		}

		return label;
	}

}
