/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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
	private final SourceListModel model;
	private JTree navigationTree;
	private SourceListControlBar controlBar;
	private static SourceList sourceList;
	private Account selectedAccount;
	private User selectedUser;

	/**
	 * Creates a new main navigation panel
	 */
	public MainNavigationPanel() {
		getUserList();
		model = updateModel();
		sourceList = new SourceList(model);
		controlBar = createControlBar();
		sourceList.installSourceListControlBar(controlBar);

	}

	private void getUserList() {
		if (App.isDatabaseReady()) {
			try {
				userList = UserFacadeDelegateFactory.getDelegate().findUsers();
			} catch (InternalErrorException e) {

			}
		}
		if (userList == null)
			userList = new ArrayList<User>();
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
		navigationTree.addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) navigationTree
						.getLastSelectedPathComponent();

				if ((node != null) && (node.isLeaf())
						&& (node.getUserObject() instanceof Account))
					selectedAccount = (Account) node.getUserObject();
				else if ((node != null)
						&& (node.getUserObject() instanceof User)) {
					selectedUser = (User) node.getUserObject();
				}
			}

		});
		navigationTree.setRootVisible(false);
		return navigationTree;
	}

	/**
	 * Gets the selected account
	 * 
	 * @return the selected account
	 */
	public Account getSelectedAccount() {

		Account theAccount = null;
		if (PlatformUtils.isMacOS()) {
			SourceListItem item = sourceList.getSelectedItem();
			if (item != null)
				theAccount = findAccountByName(item.getText());
		} else {
			theAccount = selectedAccount;
		}
		return theAccount;
	}

	/**
	 * Gets the selected user
	 * 
	 * @return the selected user
	 */
	public User getSelectedUser() {
		User theUser = null;
		if (PlatformUtils.isMacOS()) {
			SourceListItem item = sourceList.getSelectedItem();
			if (item != null)
				theUser = findUserByName(item.getText());
		} else {
			theUser = selectedUser;
		}
		return theUser;
	}

	private Account findAccountByName(String accountName) {
		for (User user : userList) {
			for (Account account : user.getAccounts()) {
				if (account.getName().equals(accountName))
					return account;
			}
		}
		return null;
	}

	private User findUserByName(String userName) {
		for (User user : userList) {
			if (user.getName().equals(userName))
				return user;
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

	private SourceListModel updateModel() {
		SourceListModel model = new SourceListModel();

		SourceListCategory usersCategory = new SourceListCategory(
				MessageManager.getMessage("NavigationPanel.Users"));
		model.addCategory(usersCategory);

		for (User user : userList) {
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
		return model;
	}

	private static SourceListControlBar createControlBar() {
		SourceListControlBar controlBar = new SourceListControlBar();
		controlBar
				.createAndAddPopdownButton(
						MacIcons.PLUS,
						new PopupMenuCustomizerUsingStrings(
								new NavigationPanelListener(),
								MessageManager
										.getMessage("NavigationPanel.AddUser"),
								MessageManager
										.getMessage("NavigationPanel.AddAccount")));
		controlBar.createAndAddPopdownButton(MacIcons.MINUS,
				new PopupMenuCustomizerUsingStrings(
						new NavigationPanelListener(), MessageManager
								.getMessage("NavigationPanel.RemoveUser"),
						MessageManager
								.getMessage("NavigationPanel.RemoveAccount")));
		controlBar.createAndAddPopdownButton(MacIcons.GEAR,
				new PopupMenuCustomizerUsingStrings(
						new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								if (e
										.getActionCommand()
										.equals(
												MessageManager
														.getMessage("NavigationPanel.Expand"))) {
									expandSourceList();

								} else if (e
										.getActionCommand()
										.equals(
												MessageManager
														.getMessage("NavigationPanel.Collapse"))) {
									collapseSourceList();
								}

							}

						}, MessageManager.getMessage("NavigationPanel.Expand"),
						MessageManager.getMessage("NavigationPanel.Collapse")));
		return controlBar;
	}

	protected static void collapseSourceList() {
		// TODO Check mac widgets API to see if this is possible
	}

	protected static void expandSourceList() {
		// TODO Check mac widgets API to see if this is possible

	}
}
