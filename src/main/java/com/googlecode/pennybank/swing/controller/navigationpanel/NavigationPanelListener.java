/**
 * NavigationPanelListener.java
 * 
 * 06/03/2009
 */
package com.googlecode.pennybank.swing.controller.navigationpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListItem;
import com.explodingpixels.macwidgets.SourceListModelListener;
import com.explodingpixels.macwidgets.SourceListSelectionListener;
import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.controller.account.AddAccountListener;
import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.controller.profile.AddUserListener;
import com.googlecode.pennybank.swing.controller.profile.RemoveUserListener;
import com.googlecode.pennybank.swing.view.main.MainNavigationPanel;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class NavigationPanelListener implements ActionListener,
		SourceListSelectionListener, TreeSelectionListener,
		SourceListModelListener {

	private MainNavigationPanel mainNavigationPanel;
	private List<User> userList;

	public NavigationPanelListener(MainNavigationPanel mainNavigationPanel) {
		this.mainNavigationPanel = mainNavigationPanel;
		userList = readUserList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());

		if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.AddUser"))) {
			new AddUserListener().actionPerformed(e);
		} else if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.AddAccount"))) {
			new AddAccountListener().actionPerformed(e);
		} else if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.RemoveUser"))) {
			new RemoveUserListener().actionPerformed(e);
		} else if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.RemoveAccount"))) {
			new RemoveAccountListener().actionPerformed(e);
		} else if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.Expand"))) {
			mainNavigationPanel.expandNavigationTree();
		} else if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.Collapse"))) {
			mainNavigationPanel.collapseNavigationTree();
		}
	}

	public void categoryAdded(SourceListCategory arg0, int arg1) {
		userList = readUserList();
	}

	public void categoryRemoved(SourceListCategory arg0) {
		userList = readUserList();
	}

	public void itemAddedToCategory(SourceListItem arg0,
			SourceListCategory arg1, int arg2) {
		userList = readUserList();
	}

	public void itemAddedToItem(SourceListItem arg0, SourceListItem arg1,
			int arg2) {
		userList = readUserList();
	}

	public void itemRemovedFromCategory(SourceListItem arg0,
			SourceListCategory arg1) {
		userList = readUserList();
	}

	public void itemRemovedFromItem(SourceListItem arg0, SourceListItem arg1) {
		userList = readUserList();
	}

	public void sourceListItemSelected(SourceListItem sourceListItem) {
		if (sourceListItem != null) {
			Account theAccount = findAccountByName(sourceListItem.getText());
			User theUser = findUserByName(sourceListItem.getText());
			mainNavigationPanel.setSelectedAccount(theAccount);
			mainNavigationPanel.setSelectedUser(theUser);
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) ((JTree) mainNavigationPanel
				.getComponent()).getLastSelectedPathComponent();

		if ((node != null) && (node.isLeaf())
				&& (node.getUserObject() instanceof Account)) {
			mainNavigationPanel.setSelectedAccount((Account) node
					.getUserObject());
			mainNavigationPanel.setSelectedUser(null);
		} else if ((node != null) && (node.getUserObject() instanceof User)) {
			mainNavigationPanel.setSelectedUser((User) node.getUserObject());
			mainNavigationPanel.setSelectedAccount(null);
		}
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

	private List<User> readUserList() {
		List<User> userList = null;
		if (App.isDatabaseReady()) {
			try {
				userList = UserFacadeDelegateFactory.getDelegate().findUsers();
			} catch (InternalErrorException e) {

			}
		}
		if (userList == null)
			userList = new ArrayList<User>();
		return userList;
	}
}
