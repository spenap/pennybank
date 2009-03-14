/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.explodingpixels.macwidgets.MacIcons;
import com.explodingpixels.macwidgets.SourceList;
import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListControlBar;
import com.explodingpixels.macwidgets.SourceListItem;
import com.explodingpixels.macwidgets.SourceListModel;
import com.explodingpixels.widgets.PopupMenuCustomizerUsingStrings;
import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.controller.navigationpanel.NavigationPanelListener;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.PlatformUtils;

/**
 * The application Content Panel
 * 
 * @author spenap
 */
public class MainNavigationPanel {

	private List<User> userList;
	private JTree navigationTree;
	private final SourceListModel model;
	private SourceListControlBar controlBar;
	private SourceList sourceList;
	private Account selectedAccount;
	private User selectedUser;
	private NavigationPanelListener navigationPanelListener;

	/**
	 * Creates a new main navigation panel
	 */
	public MainNavigationPanel() {
		userList = getUserList();
		model = new SourceListModel();
		updateModel();
		navigationPanelListener = new NavigationPanelListener(this);
		model.addSourceListModelListener(navigationPanelListener);
		sourceList = new SourceList(model);
		sourceList.addSourceListSelectionListener(navigationPanelListener);
		controlBar = createControlBar();
		sourceList.installSourceListControlBar(controlBar);
	}

	public void collapseNavigationTree() {
		// TODO Check mac widgets API to see if this is possible
	}

	public void expandNavigationTree() {
		// TODO Check mac widgets API to see if this is possible
	}

	public JComponent getComponent() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();

		for (User user : userList) {
			DefaultMutableTreeNode userItem = new DefaultMutableTreeNode(user);
			root.add(userItem);

			DefaultMutableTreeNode accountItem = new DefaultMutableTreeNode(
					MessageManager.getMessage("NavigationPanel.Accounts")
							.toUpperCase());
			userItem.add(accountItem);

			for (Account account : user.getAccounts()) {
				accountItem.add(new DefaultMutableTreeNode(account));
			}
		}

		navigationTree = new JTree(root);
		navigationTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		navigationTree.addTreeSelectionListener(navigationPanelListener);
		navigationTree.setRootVisible(false);
		return navigationTree;
	}

	/**
	 * Gets the selected account
	 * 
	 * @return the selected account
	 */
	public Account getSelectedAccount() {
		return selectedAccount;
	}

	/**
	 * Gets the selected user
	 * 
	 * @return the selected user
	 */
	public User getSelectedUser() {
		return selectedUser;
	}

	/**
	 * Gets a mac style source list
	 * 
	 * @return
	 */
	public SourceList getSourceList() {
		return sourceList;
	}

	/**
	 * @param selectedAccount
	 *            the selectedAccount to set
	 */
	public void setSelectedAccount(Account selectedAccount) {
		this.selectedAccount = selectedAccount;
		if (selectedAccount != null) {
			MainWindow.getInstance().getContentPanel().showAccountOperations(
					selectedAccount);
		}
	}

	/**
	 * @param selectedUser
	 *            the selectedUser to set
	 */
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public void update() {
		if (PlatformUtils.isMacOS()) {
			populateModel();
		} else {

		}
	}

	private SourceListControlBar createControlBar() {
		SourceListControlBar controlBar = new SourceListControlBar();

		// Add Button (User, Account)
		controlBar
				.createAndAddPopdownButton(
						MacIcons.PLUS,
						new PopupMenuCustomizerUsingStrings(
								navigationPanelListener,
								MessageManager
										.getMessage("NavigationPanel.AddUser"),
								MessageManager
										.getMessage("NavigationPanel.AddAccount")));

		// Remove Button (User, Account)
		controlBar
				.createAndAddPopdownButton(
						MacIcons.MINUS,
						new PopupMenuCustomizerUsingStrings(
								navigationPanelListener,
								MessageManager
										.getMessage("NavigationPanel.RemoveUser"),
								MessageManager
										.getMessage("NavigationPanel.RemoveAccount")));

		// Gear Button (View options)
		controlBar.createAndAddPopdownButton(MacIcons.GEAR,
				new PopupMenuCustomizerUsingStrings(navigationPanelListener,
						MessageManager.getMessage("NavigationPanel.Expand"),
						MessageManager.getMessage("NavigationPanel.Collapse")));

		return controlBar;
	}

	private List<User> getUserList() {
		List<User> userList = null;
		if (App.isDatabaseReady()) {
			try {
				userList = UserFacadeDelegateFactory.getDelegate().findUsers();
			} catch (InternalErrorException e) {

			}
		}
		if (userList == null)
			userList = new ArrayList<User>();
		this.userList = userList;
		return userList;
	}

	private void populateModel() {
		List<SourceListCategory> categories = model.getCategories();

		for (int i = 0; i < categories.size(); i++) {
			SourceListCategory category = categories.get(i);
			model.removeCategory(category);
		}

		SourceListCategory usersCategory = new SourceListCategory(
				MessageManager.getMessage("NavigationPanel.Users"));
		model.addCategory(usersCategory);

		for (User user : getUserList()) {
			SourceListItem userItem = new SourceListItem(user.getName(),
					IconManager.getIcon("navigation_user"));
			model.addItemToCategory(userItem, usersCategory);

			SourceListItem accountItem = new SourceListItem(MessageManager
					.getMessage("NavigationPanel.Accounts").toUpperCase(),
					IconManager.getIcon("navigation_account"));
			model.addItemToItem(accountItem, userItem);

			for (Account account : user.getAccounts()) {
				model
						.addItemToItem(new SourceListItem(account.getName(),
								IconManager.getIcon("navigation_account")),
								accountItem);
			}
		}
	}

	private SourceListModel updateModel() {
		SourceListModel model = new SourceListModel();
		populateModel();
		return model;
	}
}
