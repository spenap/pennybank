/**
 * 
 */
package com.googlecode.pennybank.swing.controller.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import com.googlecode.pennybank.swing.view.accountoperationtable.AccountOperationTableFilter;

/**
 * @author spenap
 * 
 */
public class TableSearchActionListener implements ActionListener {

	private JPopupMenu tableSearchPopupMenu = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JCheckBoxMenuItem) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
			updateItemsState(item);
			if (item.equals(AccountOperationTableFilter
					.getSearchCommentsMenuItem())) {
				AccountOperationTableFilter
						.setSearchMode(AccountOperationTableFilter.SearchMode.Comments);
			} else if (item.equals(AccountOperationTableFilter
					.getSearchCategoriesMenuItem())) {
				AccountOperationTableFilter
						.setSearchMode(AccountOperationTableFilter.SearchMode.Category);
			} else if (item.equals(AccountOperationTableFilter
					.getSearchAllMenuItem())) {
				AccountOperationTableFilter
						.setSearchMode(AccountOperationTableFilter.SearchMode.All);
			}
		}
	}

	private void updateItemsState(JCheckBoxMenuItem selectedItem) {
		for (MenuElement menuItem : tableSearchPopupMenu.getSubElements()) {
			JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) menuItem;
			checkBoxMenuItem.setSelected(menuItem.equals(selectedItem));
		}
	}

	public void setPopupMenu(JPopupMenu searchPopupMenu) {
		this.tableSearchPopupMenu = searchPopupMenu;
	}
}
