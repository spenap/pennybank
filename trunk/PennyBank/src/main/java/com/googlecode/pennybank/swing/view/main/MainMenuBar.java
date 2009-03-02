/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.swing.controller.account.AddAccountListener;
import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.controller.accountoperation.AddAccountOperationListener;
import com.googlecode.pennybank.swing.controller.importation.ImportFileListener;
import com.googlecode.pennybank.swing.controller.importation.ImportFileListener.FileType;
import com.googlecode.pennybank.swing.controller.profile.AddProfileListener;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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

		// File Menu
		JMenu fileMenu = new JMenu(MessageManager
				.getMessage("MainMenu.FileMenu"));
		JMenu importMenu = new JMenu(MessageManager
				.getMessage("MainMenu.FileMenu.ImportMenu"));
		fileMenu.add(importMenu);

		JMenuItem importPListMenuItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.FileMenu.ImportMenu.PList"));
		importPListMenuItem.addActionListener(new ImportFileListener(
				FileType.PLIST));
		importMenu.add(importPListMenuItem);

		add(fileMenu);

		// Profile Menu
		JMenu profileMenu = new JMenu(MessageManager
				.getMessage("MainMenu.UserMenu"));
		JMenuItem addProfileItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.UserMenu.AddUser"));
		addProfileItem.addActionListener(new AddProfileListener());
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
				Type.DEPOSIT));

		JMenuItem withdrawFromAccountItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.AccountsMenu.WithdrawFromAccount"));
		accountsMenu.add(withdrawFromAccountItem);
		withdrawFromAccountItem
				.addActionListener(new AddAccountOperationListener(
						Type.WITHDRAW));

		JMenuItem transferItem = new JMenuItem(MessageManager
				.getMessage("MainMenu.AccountsMenu.Transfer"));
		accountsMenu.add(transferItem);

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

		// Help Menu
		JMenu helpMenu = new JMenu(MessageManager
				.getMessage("MainMenu.HelpMenu"));
		add(helpMenu);
	}
}
