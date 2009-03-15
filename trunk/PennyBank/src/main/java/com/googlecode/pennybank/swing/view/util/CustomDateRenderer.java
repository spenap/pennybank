/**
 * 
 */
package com.googlecode.pennybank.swing.view.util;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author spenap
 * 
 */
@SuppressWarnings("serial")
public class CustomDateRenderer extends DefaultTableCellRenderer {

	private DateFormat dateFormat;

	public CustomDateRenderer() {
		dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Date date = (Date) value;
		String formattedValue = dateFormat.format(date);
		JLabel label = new JLabel(formattedValue);

		if (isSelected) {
			label.setBackground(table.getSelectionBackground());
			label.setOpaque(true);
			label.setForeground(table.getSelectionForeground());
		}

		return label;
	}

}
