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
import com.googlecode.pennybank.swing.controller.accountoperation.AddAccountOperationListener.OperationType;
import com.googlecode.pennybank.swing.controller.importation.ImportFileListener;
import com.googlecode.pennybank.swing.controller.profile.AddUserListener;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * The main MenuBar for the application
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar {

	/**
	 * Creates a new MenuBar
	 */
	public MainMenuBar() {
		initComponents();
	}

	private void initComponents() {

		// Profile Menu
		JMenu profileMenu = new JMenu(MessageManager
				.getMessage("MainMenu.UserMenu"));
		JMenuItem addProfileItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.UserMenu.AddUser"));
		addProfileItem.addActionListener(new AddUserListener());
		profileMenu.add(addProfileItem);

		JMenuItem editProfileItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.UserMenu.EditUser"));
		profileMenu.add(editProfileItem);

		JMenuItem delProfileItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.UserMenu.RemoveUser"));
		profileMenu.add(delProfileItem);

		add(profileMenu);

		// Accounts Menu
		JMenu accountsMenu = new JMenu(MessageManager
				.getMessage("MainMenu.AccountsMenu"));
		JMenuItem addAccountItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.AccountsMenu.AddAccount"));
		addAccountItem.addActionListener(new AddAccountListener());
		accountsMenu.add(addAccountItem);

		JMenuItem editAccountItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.AccountsMenu.EditAccount"));
		accountsMenu.add(editAccountItem);

		JMenuItem delAccountItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.AccountsMenu.RemoveAccount"));
		delAccountItem.addActionListener(new RemoveAccountListener());
		accountsMenu.add(delAccountItem);

		accountsMenu.addSeparator();

		JMenuItem addToAccountItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.AccountsMenu.AddToAccount"));
		accountsMenu.add(addToAccountItem);
		addToAccountItem.addActionListener(new AddAccountOperationListener(
				OperationType.DEPOSIT));

		JMenuItem withdrawFromAccountItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.AccountsMenu.WithdrawFromAccount"));
		accountsMenu.add(withdrawFromAccountItem);
		withdrawFromAccountItem
				.addActionListener(new AddAccountOperationListener(
						OperationType.WITHDRAW));

		JMenuItem transferItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.AccountsMenu.Transfer"));
		accountsMenu.add(transferItem);
		transferItem.addActionListener(new AddAccountOperationListener(
				OperationType.TRANSFER));

		add(accountsMenu);

		// Statistics Menu
		JMenu statisticsMenu = new JMenu(MessageManager
				.getMessage("MainMenu.StatisticsMenu"));
		JMenuItem summaryItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.StatisticsMenu.Summary"));
		statisticsMenu.add(summaryItem);

		JMenuItem incomesItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.StatisticsMenu.Incomes"));
		statisticsMenu.add(incomesItem);

		JMenuItem expensesItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.StatisticsMenu.Expenses"));
		statisticsMenu.add(expensesItem);

		add(statisticsMenu);

		// Tools Menu
		JMenu toolsMenu = new JMenu(MessageManager
				.getMessage("MainMenu.ToolsMenu"));
		JMenuItem importMenuItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.ToolsMenu.Import"));
		importMenuItem.addActionListener(new ImportFileListener());
		importMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.SHIFT_MASK | ActionEvent.META_MASK));
		toolsMenu.add(importMenuItem);

		add(toolsMenu);

		// Help Menu
		JMenu helpMenu = new JMenu(MessageManager
				.getMessage("MainMenu.HelpMenu"));
		add(helpMenu);
	}
}
