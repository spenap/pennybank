/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.TriAreaComponent;
import com.googlecode.pennybank.swing.controller.account.AddAccountListener;
import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.controller.accountoperation.AddAccountOperationListener;
import com.googlecode.pennybank.swing.controller.accountoperation.AddAccountOperationListener.OperationType;
import com.googlecode.pennybank.swing.controller.profile.AddUserListener;
import com.googlecode.pennybank.swing.controller.profile.RemoveUserListener;
import com.googlecode.pennybank.swing.view.accountoperationtable.AccountOperationTableFilter;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.PlatformUtils;

/**
 * The main ToolBar for the application
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainToolBar extends Component {

	private JButton addUserButton;
	private JButton delUserButton;
	private JButton addAccountButton;
	private JButton removeAccountButton;
	private JButton addToAccountButton;
	private JButton withdrawFromAccountButton;
	private JButton transferBetweenAccountsButton;

	public MainToolBar() {

	}

	/**
	 * Gets the ToolBar
	 * 
	 * @param mainFrame
	 *            the frame to insert the toolBar into
	 * @return the toolBar
	 */
	public Component getComponent(JFrame mainFrame) {

		List<JButton> leftButtons = new ArrayList<JButton>();
		List<JButton> rightButtons = new ArrayList<JButton>();

		// User buttons
		leftButtons.add(getAddUserButton());
		leftButtons.add(getDelUserButton());

		// Account buttons
		leftButtons.add(getAddAccountButton());
		leftButtons.add(getRemoveAccountButton());

		// Account operation buttons
		leftButtons.add(getAddToAccountButton());
		leftButtons.add(getWithdrawFromAccountButton());
		leftButtons.add(getTransferBetweenAccountsButton());

		setAccountEnabled(false);
		setUserEnabled(false);

		if (PlatformUtils.isMacOS()) {
			return populateOSXToolBar(mainFrame, leftButtons, rightButtons)
					.getComponent();
		} else {
			return populateJToolBar(mainFrame, leftButtons, rightButtons);
		}
	}

	public void setUserEnabled(boolean value) {
		getDelUserButton().setEnabled(value);
		getAddAccountButton().setEnabled(value);
	}

	public void setAccountEnabled(boolean value) {
		getRemoveAccountButton().setEnabled(value);
		getAddToAccountButton().setEnabled(value);
		getWithdrawFromAccountButton().setEnabled(value);
		getTransferBetweenAccountsButton().setEnabled(value);
	}

	private JButton getAddAccountButton() {
		if (addAccountButton == null) {
			addAccountButton = new JButton(MessageManager
					.getMessage("MainToolbar.Accounts.AddAccount"), IconManager
					.getIcon("toolbar_add_account"));
			addAccountButton.addActionListener(new AddAccountListener());
		}
		return addAccountButton;
	}

	private JButton getAddToAccountButton() {
		if (addToAccountButton == null) {
			addToAccountButton = new JButton(MessageManager
					.getMessage("MainToolbar.Accounts.AddToAccount"),
					IconManager.getIcon("toolbar_deposit"));
			addToAccountButton
					.addActionListener(new AddAccountOperationListener(
							OperationType.DEPOSIT));
		}
		return addToAccountButton;
	}

	private JButton getAddUserButton() {
		if (addUserButton == null) {
			addUserButton = new JButton(MessageManager
					.getMessage("MainToolbar.Users.AddUser"), IconManager
					.getIcon("toolbar_add_user"));
			addUserButton.addActionListener(new AddUserListener());
		}
		return addUserButton;
	}

	private JButton getDelUserButton() {
		if (delUserButton == null) {
			delUserButton = new JButton(MessageManager
					.getMessage("MainToolbar.Users.RemoveUser"), IconManager
					.getIcon("toolbar_del_user"));
			delUserButton.addActionListener(new RemoveUserListener());
		}
		return delUserButton;
	}

	private JButton getRemoveAccountButton() {
		if (removeAccountButton == null) {
			removeAccountButton = new JButton(MessageManager
					.getMessage("MainToolbar.Accounts.RemoveAccount"),
					IconManager.getIcon("toolbar_del_account"));
			removeAccountButton.addActionListener(new RemoveAccountListener());
		}
		return removeAccountButton;
	}

	private JButton getTransferBetweenAccountsButton() {
		if (transferBetweenAccountsButton == null) {
			transferBetweenAccountsButton = new JButton(
					MessageManager
							.getMessage("MainToolbar.Accounts.TransferBetweenAccounts"),
					IconManager.getIcon("toolbar_transfer"));
			transferBetweenAccountsButton
					.addActionListener(new AddAccountOperationListener(
							OperationType.TRANSFER));
		}
		return transferBetweenAccountsButton;
	}

	private JButton getWithdrawFromAccountButton() {
		if (withdrawFromAccountButton == null) {
			withdrawFromAccountButton = new JButton(MessageManager
					.getMessage("MainToolbar.Accounts.WithdrawFromAccount"),
					IconManager.getIcon("toolbar_withdraw"));
			withdrawFromAccountButton
					.addActionListener(new AddAccountOperationListener(
							OperationType.WITHDRAW));
		}
		return withdrawFromAccountButton;
	}

	private JToolBar populateJToolBar(JFrame mainFrame,
			List<JButton> leftButtons, List<JButton> rightButtons) {

		JToolBar jToolBar = new JToolBar();
		mainFrame.add(jToolBar);

		// Left side buttons
		for (JButton button : leftButtons) {
			jToolBar.add(button);
		}

		// Right side buttons
		for (JButton button : rightButtons) {
			jToolBar.add(button);
		}

		return jToolBar;
	}

	private TriAreaComponent populateOSXToolBar(JFrame mainFrame,
			List<JButton> leftButtons, List<JButton> rightButtons) {

		TriAreaComponent osxToolBar = MacWidgetFactory.createUnifiedToolBar();
		osxToolBar.installWindowDraggerOnWindow(mainFrame);

		// Left side buttons
		for (JButton button : leftButtons) {
			osxToolBar.addComponentToLeft(MacButtonFactory
					.makeUnifiedToolBarButton(button));
		}

		for (JButton button : rightButtons) {
			osxToolBar.addComponentToRight(button);
		}

		// Right side buttons
		osxToolBar.addComponentToRight(AccountOperationTableFilter
				.getSearchField());
		return osxToolBar;
	}
}
