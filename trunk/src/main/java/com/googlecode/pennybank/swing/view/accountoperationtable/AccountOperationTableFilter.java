package com.googlecode.pennybank.swing.view.accountoperationtable;

import java.awt.Dimension;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.googlecode.pennybank.swing.controller.main.TableSearchActionListener;
import com.googlecode.pennybank.swing.view.util.MessageManager;

public class AccountOperationTableFilter {

	public enum SearchMode {
		Comments, Category, All
	};

	private static JTextField searchField;
	private static SearchMode currentSearchMode = SearchMode.All;
	private static JCheckBoxMenuItem searchCommentsMenuItem;
	private static JCheckBoxMenuItem searchCategoriesMenuItem;
	private static JCheckBoxMenuItem searchAllMenuItem;
	private static TableSearchActionListener tableSearchActionListener = null;

	public static JTextField getSearchField() {
		if (searchField == null) {
			searchField = new JTextField();
			searchField.setPreferredSize(new Dimension(150, 20));
			searchField.putClientProperty("JTextField.variant", "search");

			JPopupMenu searchPopupMenu = getSearchPopup();
			getTableSearchActionListener().setPopupMenu(searchPopupMenu);

			searchField.putClientProperty("JTextField.Search.FindPopup",
					searchPopupMenu);
		}
		return searchField;

	}

	public static SearchMode getSearchMode() {
		return currentSearchMode;
	}

	public static void setSearchMode(SearchMode mode) {
		currentSearchMode = mode;
		String oldSearch = searchField.getText();
		searchField.setText(null);
		searchField.setText(oldSearch);

	}

	public static JMenuItem getSearchAllMenuItem() {
		if (searchAllMenuItem == null) {
			searchAllMenuItem = new JCheckBoxMenuItem(MessageManager
					.getMessage("Search.All"));
			searchAllMenuItem.addActionListener(getTableSearchActionListener());
		}
		return searchAllMenuItem;
	}

	public static JMenuItem getSearchCategoriesMenuItem() {
		if (searchCategoriesMenuItem == null) {
			searchCategoriesMenuItem = new JCheckBoxMenuItem(MessageManager
					.getMessage("Search.Categories"));
			searchCategoriesMenuItem
					.addActionListener(getTableSearchActionListener());
		}
		return searchCategoriesMenuItem;
	}

	public static JMenuItem getSearchCommentsMenuItem() {
		if (searchCommentsMenuItem == null) {
			searchCommentsMenuItem = new JCheckBoxMenuItem(MessageManager
					.getMessage("Search.Comments"));
			searchCommentsMenuItem
					.addActionListener(getTableSearchActionListener());
		}
		return searchCommentsMenuItem;
	}

	private static JPopupMenu getSearchPopup() {

		// Search popup menu
		JPopupMenu searchPopupMenu = new JPopupMenu(MessageManager
				.getMessage("Search.Title"));
		searchPopupMenu.setBorder(new EmptyBorder(8, 6, 8, 6));

		searchPopupMenu.add(getSearchCommentsMenuItem());
		searchPopupMenu.add(getSearchCategoriesMenuItem());
		searchPopupMenu.add(getSearchAllMenuItem());

		return searchPopupMenu;
	}

	private static TableSearchActionListener getTableSearchActionListener() {
		if (tableSearchActionListener == null) {
			tableSearchActionListener = new TableSearchActionListener();
		}
		return tableSearchActionListener;
	}
}
