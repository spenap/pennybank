/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import java.util.ArrayList;
import java.util.List;

import com.explodingpixels.macwidgets.MacIcons;
import com.explodingpixels.macwidgets.SourceList;
import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListControlBar;
import com.explodingpixels.macwidgets.SourceListItem;
import com.explodingpixels.macwidgets.SourceListModel;
import com.explodingpixels.widgets.PopupMenuCustomizerUsingStrings;
import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.controller.account.AddAccountListener;
import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * The application Content Panel
 * 
 * @author spenap
 */
public class MainNavigationPanel {

	private static SourceListControlBar createControlBar() {
		SourceListControlBar controlBar = new SourceListControlBar();
		controlBar.createAndAddButton(MacIcons.PLUS, new AddAccountListener());
		controlBar.createAndAddButton(MacIcons.MINUS,
				new RemoveAccountListener());
		controlBar.createAndAddPopdownButton(MacIcons.GEAR,
				new PopupMenuCustomizerUsingStrings(null, "Element 1",
						"Element 2", "Element 3"));
		return controlBar;
	}

	private SourceListCategory accountsCategory;
	private final SourceListModel model;
	private List<SourceListItem> sourceListItems;
	private SourceListControlBar controlBar;
	private SourceList sourceList;

	/**
	 * Creates a new main navigation panel
	 */
	public MainNavigationPanel() {
		accountsCategory = new SourceListCategory(MessageManager
				.getMessage("NavigationPanel.Accounts"));
		model = new SourceListModel();
		sourceListItems = new ArrayList<SourceListItem>();

		populateAccountList();
		sourceList = new SourceList(model);
		controlBar = createControlBar();
		sourceList.installSourceListControlBar(controlBar);

	}

	/**
	 * Adds an account to the navigation panel
	 * 
	 * @param theAccount
	 */
	public void addAccount(Account theAccount) {
		SourceListItem item = new SourceListItem(theAccount.getName(),
				IconManager.getIcon("navigation_account"));
		sourceListItems.add(item);
		model.addItemToCategory(item, accountsCategory);
	}

	/**
	 * Gets the selected account
	 * 
	 * @return the selected account
	 */
	public Account getSelectedAccount() {
		SourceListItem item = sourceList.getSelectedItem();
		if (item == null)
			return null;
		for (Account account : App.getCurrentUser().getAccounts()) {
			if (account.getName().equals(item.getText()))
				return account;
		}
		return null;
	}

	/**
	 * Gets a mac style source list
	 * 
	 * @return
	 */
	public SourceList getSourceList() {
		return sourceList;
	}

	private void populateAccountList() {
		if (model.getCategories().contains(accountsCategory)) {
			model.removeCategory(accountsCategory);
		}
		model.addCategory(accountsCategory);
		for (SourceListItem sourceListItem : sourceListItems) {
			model.addItemToCategory(sourceListItem, accountsCategory);
		}
	}

	/**
	 * Removes an account from the navigation panel
	 * 
	 * @param theAccount
	 */
	public void removeAccount(Account theAccount) {
		int itemIndex = -1;

		for (int i = 0; i < sourceListItems.size(); i++) {
			if (sourceListItems.get(i).getText().equals(theAccount.getName())) {
				itemIndex = i;
				break;
			}
		}
		sourceListItems.remove(itemIndex);
		populateAccountList();
	}
}
