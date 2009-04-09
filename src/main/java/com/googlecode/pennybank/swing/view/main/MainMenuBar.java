/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.googlecode.pennybank.swing.controller.account.AddAccountListener;
import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.controller.accountoperation.AddAccountOperationListener;
import com.googlecode.pennybank.swing.controller.accountoperation.DeleteAccountOperationListener;
import com.googlecode.pennybank.swing.controller.accountoperation.EditAccountOperationListener;
import com.googlecode.pennybank.swing.controller.accountoperation.AddAccountOperationListener.OperationType;
import com.googlecode.pennybank.swing.controller.category.ManageCategoriesListener;
import com.googlecode.pennybank.swing.controller.importation.ImportFileListener;
import com.googlecode.pennybank.swing.controller.main.RedoActionListener;
import com.googlecode.pennybank.swing.controller.main.UndoActionListener;
import com.googlecode.pennybank.swing.controller.profile.AddUserListener;
import com.googlecode.pennybank.swing.controller.profile.RemoveUserListener;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * The main MenuBar for the application
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar {

	private JMenu fileMenu;
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem closeMenuItem;
	private JMenuItem importMenuItem;
	private JMenuItem exportMenuItem;
	private JMenu editMenu;
	private JMenuItem undoMenuItem;
	private JMenuItem redoMenuItem;
	private JMenuItem cutMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem pasteMenuItem;
	private JMenuItem deleteMenuItem;
	private JMenuItem selectAllMenuItem;
	private JMenu userMenu;
	private JMenuItem addUserItem;
	private JMenuItem editUserItem;
	private JMenuItem removeUserItem;
	private JMenu accountsMenu;
	private JMenuItem addAccountItem;
	private JMenuItem editAccountItem;
	private JMenuItem removeAccountItem;
	private JMenuItem manageCategoriesMenuItem;
	private JMenuItem addToAccountItem;
	private JMenuItem withdrawFromAccountItem;
	private JMenuItem transferItem;
	private JMenuItem editAccountOperationItem;
	private JMenuItem removeAccountOperationItem;
	private JMenu statisticsMenu;
	private JMenuItem incomesItem;
	private JMenuItem expensesItem;
	private JMenu helpMenu;

	/**
	 * Creates a new MenuBar
	 */
	public MainMenuBar() {
		initComponents();
	}

	/**
	 * Updates the menu items status depending on whether an account is selected
	 * or not
	 * 
	 * @param accountSelected
	 */
	public void setAccountEnabled(boolean accountSelected) {
		getEditAccountItem().setEnabled(accountSelected);
		getRemoveAccountItem().setVisible(accountSelected);
		getRemoveAccountItem().setEnabled(accountSelected);
		getAddToAccountItem().setEnabled(accountSelected);
		getWithdrawFromAccountItem().setEnabled(accountSelected);
		getTransferItem().setEnabled(accountSelected);
		if (accountSelected) {
			getDeleteMenuItem().setVisible(!accountSelected);
		}
	}

	public void setOperationEnabled(boolean operationSelected) {
		getEditAccountOperationItem().setEnabled(operationSelected);
		getRemoveAccountOperationItem().setVisible(operationSelected);
		getRemoveAccountOperationItem().setEnabled(operationSelected);

		if (operationSelected) {
			getDeleteMenuItem().setVisible(!operationSelected);
			setAccountEnabled(!operationSelected);
		}
	}

	/**
	 * Updates the menu items status depending on whether an user is selected or
	 * not
	 * 
	 * @param userSelected
	 */
	public void setUserEnabled(boolean userSelected) {
		getEditUserItem().setEnabled(userSelected);
		getRemoveUserItem().setVisible(userSelected);
		getRemoveUserItem().setEnabled(userSelected);
		getAddAccountItem().setEnabled(userSelected);
		if (userSelected) {
			getDeleteMenuItem().setVisible(!userSelected);
		}
	}

	public void setRedoText(String redoAction) {
		StringBuilder text = new StringBuilder(MessageManager
				.getMessage("MainMenu.EditMenu.Redo"));
		if (redoAction != null) {
			text.append(" " + redoAction);
		}
		getRedoMenuItem().setEnabled(redoAction != null);
		getRedoMenuItem().setText(text.toString());
	}

	public void setUndoText(String undoAction) {
		StringBuilder text = new StringBuilder(MessageManager
				.getMessage("MainMenu.EditMenu.Undo"));
		if (undoAction != null) {
			text.append(" " + undoAction);
		}
		getUndoMenuItem().setEnabled(undoAction != null);
		getUndoMenuItem().setText(text.toString());
	}

	private JMenu getAccountsMenu() {
		if (accountsMenu == null) {
			accountsMenu = new JMenu(MessageManager
					.getMessage("MainMenu.AccountsMenu"));
			accountsMenu.add(getAddAccountItem());
			accountsMenu.add(getEditAccountItem());

			accountsMenu.addSeparator();

			accountsMenu.add(getManageCategoriesMenuItem());

			accountsMenu.addSeparator();

			accountsMenu.add(getAddToAccountItem());
			accountsMenu.add(getWithdrawFromAccountItem());
			accountsMenu.add(getTransferItem());
			accountsMenu.add(getEditAccountOperationItem());

		}
		return accountsMenu;
	}

	private JMenuItem getAddAccountItem() {
		if (addAccountItem == null) {
			addAccountItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.AddAccount"));
			addAccountItem.addActionListener(new AddAccountListener());
		}
		return addAccountItem;
	}

	private JMenuItem getAddToAccountItem() {
		if (addToAccountItem == null) {
			addToAccountItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.AddToAccount"));
			addToAccountItem.addActionListener(new AddAccountOperationListener(
					OperationType.DEPOSIT));
		}
		return addToAccountItem;
	}

	private JMenuItem getAddUserItem() {
		if (addUserItem == null) {
			addUserItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.UserMenu.AddUser"));
			addUserItem.addActionListener(new AddUserListener());
		}
		return addUserItem;
	}

	private JMenuItem getCloseMenuItem() {
		if (closeMenuItem == null) {
			closeMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.FileMenu.Close"));
			closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
					ActionEvent.META_MASK));
		}
		return closeMenuItem;
	}

	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.EditMenu.Copy"));
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					ActionEvent.META_MASK));
		}
		return copyMenuItem;
	}

	private JMenu getCustomHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu(MessageManager.getMessage("MainMenu.HelpMenu"));
		}
		return helpMenu;
	}

	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.EditMenu.Cut"));
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					ActionEvent.META_MASK));
		}
		return cutMenuItem;
	}

	private JMenuItem getDeleteMenuItem() {
		if (deleteMenuItem == null) {
			deleteMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.EditMenu.Delete"));
			deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_DELETE, 0));
			deleteMenuItem.setEnabled(false);
		}
		return deleteMenuItem;
	}

	private JMenuItem getEditAccountItem() {
		if (editAccountItem == null) {
			editAccountItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.EditAccount"));
		}
		return editAccountItem;
	}

	private JMenuItem getEditAccountOperationItem() {
		if (editAccountOperationItem == null) {
			editAccountOperationItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.EditAccountOperation"));
			editAccountOperationItem
					.addActionListener(new EditAccountOperationListener());
		}
		return editAccountOperationItem;
	}

	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu(MessageManager.getMessage("MainMenu.EditMenu"));
			editMenu.add(getUndoMenuItem());
			editMenu.add(getRedoMenuItem());
			editMenu.addSeparator();
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
			editMenu.add(getDeleteMenuItem());
			editMenu.add(getRemoveAccountItem());
			editMenu.add(getRemoveAccountOperationItem());
			editMenu.add(getRemoveUserItem());
			editMenu.add(getSelectAllMenuItem());
		}
		return editMenu;
	}

	private JMenuItem getEditUserItem() {
		if (editUserItem == null) {
			editUserItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.UserMenu.EditUser"));
		}
		return editUserItem;
	}

	private JMenuItem getExportMenuItem() {
		if (exportMenuItem == null) {
			exportMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.FileMenu.Export"));
			exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
					ActionEvent.SHIFT_MASK | ActionEvent.META_MASK));
		}
		return exportMenuItem;
	}

	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu(MessageManager.getMessage("MainMenu.FileMenu"));
			fileMenu.add(getNewMenuItem());
			fileMenu.add(getOpenMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getCloseMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getImportMenuItem());
			fileMenu.add(getExportMenuItem());
		}
		return fileMenu;
	}

	private JMenuItem getImportMenuItem() {
		if (importMenuItem == null) {
			importMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.FileMenu.Import"));
			importMenuItem.addActionListener(new ImportFileListener());
			importMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
					ActionEvent.SHIFT_MASK | ActionEvent.META_MASK));
		}
		return importMenuItem;
	}

	private JMenuItem getManageCategoriesMenuItem() {
		if (manageCategoriesMenuItem == null) {
			manageCategoriesMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.ManageCategories"));
			manageCategoriesMenuItem
					.addActionListener(new ManageCategoriesListener());
			manageCategoriesMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_C, ActionEvent.SHIFT_MASK
							| ActionEvent.META_MASK));
		}
		return manageCategoriesMenuItem;
	}

	private JMenuItem getNewMenuItem() {
		if (newMenuItem == null) {
			newMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.FileMenu.New"));
			newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					ActionEvent.META_MASK));
		}
		return newMenuItem;
	}

	private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.FileMenu.Open"));
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					ActionEvent.META_MASK));
		}
		return openMenuItem;
	}

	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.EditMenu.Paste"));
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					ActionEvent.META_MASK));
		}
		return pasteMenuItem;
	}

	private JMenuItem getRedoMenuItem() {
		if (redoMenuItem == null) {
			redoMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.EditMenu.Redo"));
			redoMenuItem.addActionListener(new RedoActionListener());
			redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					ActionEvent.META_MASK | ActionEvent.SHIFT_MASK));
			redoMenuItem.setEnabled(false);
		}
		return redoMenuItem;
	}

	private JMenuItem getRemoveAccountItem() {
		if (removeAccountItem == null) {
			removeAccountItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.RemoveAccount"));
			removeAccountItem.addActionListener(new RemoveAccountListener());
			removeAccountItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_DELETE, 0));
		}
		return removeAccountItem;
	}

	private JMenuItem getRemoveAccountOperationItem() {
		if (removeAccountOperationItem == null) {
			removeAccountOperationItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.RemoveAccountOperation"));
			removeAccountOperationItem
					.addActionListener(new DeleteAccountOperationListener());
			removeAccountOperationItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_DELETE, 0));
		}
		return removeAccountOperationItem;
	}

	private JMenuItem getRemoveUserItem() {
		if (removeUserItem == null) {
			removeUserItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.UserMenu.RemoveUser"));
			removeUserItem.addActionListener(new RemoveUserListener());
			removeUserItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_DELETE, 0));
		}
		return removeUserItem;
	}

	private JMenuItem getSelectAllMenuItem() {
		if (selectAllMenuItem == null) {
			selectAllMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.EditMenu.SelectAll"));
			selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_A, ActionEvent.META_MASK));
		}
		return selectAllMenuItem;
	}

	private JMenu getStatisticsMenu() {
		if (statisticsMenu == null) {
			statisticsMenu = new JMenu(MessageManager
					.getMessage("MainMenu.StatisticsMenu"));
			JMenuItem summaryItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.StatisticsMenu.Summary"));
			statisticsMenu.add(summaryItem);

			incomesItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.StatisticsMenu.Incomes"));
			statisticsMenu.add(incomesItem);

			expensesItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.StatisticsMenu.Expenses"));
			statisticsMenu.add(expensesItem);
		}
		return statisticsMenu;
	}

	private JMenuItem getTransferItem() {
		if (transferItem == null) {
			transferItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.Transfer"));
			transferItem.addActionListener(new AddAccountOperationListener(
					OperationType.TRANSFER));
		}
		return transferItem;
	}

	private JMenuItem getUndoMenuItem() {
		if (undoMenuItem == null) {
			undoMenuItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.EditMenu.Undo"));
			undoMenuItem.addActionListener(new UndoActionListener());
			undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					ActionEvent.META_MASK));
			undoMenuItem.setEnabled(false);
		}
		return undoMenuItem;
	}

	private JMenu getUserMenu() {
		if (userMenu == null) {
			userMenu = new JMenu(MessageManager.getMessage("MainMenu.UserMenu"));
			userMenu.add(getAddUserItem());
			userMenu.add(getEditUserItem());
		}
		return userMenu;
	}

	private JMenuItem getWithdrawFromAccountItem() {
		if (withdrawFromAccountItem == null) {
			withdrawFromAccountItem = new JMenuItem(MessageManager
					.getMessage("MainMenu.AccountsMenu.WithdrawFromAccount"));
			withdrawFromAccountItem
					.addActionListener(new AddAccountOperationListener(
							OperationType.WITHDRAW));
		}
		return withdrawFromAccountItem;
	}

	private void initComponents() {
		add(getFileMenu());
		add(getEditMenu());
		add(getUserMenu());
		add(getAccountsMenu());
		add(getStatisticsMenu());
		add(getCustomHelpMenu());
		setUserEnabled(false);
		setAccountEnabled(false);
		setOperationEnabled(false);
	}
}
