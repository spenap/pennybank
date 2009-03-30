/**
 * AccountWindow.java
 * 
 * 03/03/2009
 */
package com.googlecode.pennybank.swing.view.account;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.exceptions.BadNameException;

/**
 * @author spenap
 * 
 */
public class AccountWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel mainContentPane = null;
	private JPanel fieldsPane = null;
	private JPanel buttonsPane = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private JPanel componentsPane = null;
	private JLabel iconLabel = null;
	private JLabel accountNameLabel = null;
	private JLabel accountBalanceLabel = null;
	private JTextField accountNameTextField = null;
	private JTextField accountBalanceTextField = null;
	private User theUser;
	private PersistMode persistMode = null;
	private Account theAccount = null;

	private enum PersistMode {
		CreateAccount, UpdateAccount
	};

	/**
	 * @param owner
	 */
	public AccountWindow(Frame owner, User user) {
		super(owner);
		this.theUser = user;
		this.persistMode = PersistMode.CreateAccount;
		this.theAccount = new Account(theUser, 0, "");
		initialize(owner);
	}

	public AccountWindow(Frame owner, User user, Account toUpdate) {
		super(owner);
		this.theUser = user;
		this.persistMode = PersistMode.UpdateAccount;
		this.theAccount = toUpdate;
		initialize(owner);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(Frame owner) {
		this.setResizable(false);
		this.setSize(333, 170);
		this.setLocationRelativeTo(owner);
		this.setTitle(MessageManager.getMessage("AccountWindow."
				+ persistMode.toString() + ".Title"));
		this.setModal(true);
		this.setContentPane(getMainContentPane());
	}

	/**
	 * This method initializes mainContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainContentPane() {
		if (mainContentPane == null) {
			mainContentPane = new JPanel();
			mainContentPane.setLayout(new BorderLayout());
			mainContentPane.add(getFieldsPane(), BorderLayout.CENTER);
		}
		return mainContentPane;
	}

	/**
	 * This method initializes fieldsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getFieldsPane() {
		if (fieldsPane == null) {
			fieldsPane = new JPanel();
			fieldsPane.setLayout(new BorderLayout());
			fieldsPane.add(getButtonsPane(), BorderLayout.SOUTH);
			fieldsPane.add(getComponentsPane(), BorderLayout.CENTER);
			fieldsPane.add(getIconLabel(), BorderLayout.WEST);
		}
		return fieldsPane;
	}

	/**
	 * This method initializes buttonsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonsPane() {
		if (buttonsPane == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			buttonsPane = new JPanel();
			buttonsPane.setLayout(flowLayout);
			buttonsPane.add(getCancelButton(), null);
			buttonsPane.add(getOkButton(), null);
		}
		return buttonsPane;
	}

	/**
	 * This method initializes okButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(MessageManager.getMessage("okButton"));
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					okButtonActionPerformed(e);
				}
			});
		}
		return okButton;
	}

	protected void okButtonActionPerformed(ActionEvent e) {
		try {
			String accountName = getAccountNameTextField().getText();
			if (accountName == null || accountName.trim().equals(""))
				throw new BadNameException();
			double accountBalance = NumberFormat.getInstance().parse(
					getAccountBalanceTextField().getText()).doubleValue();
			theAccount.setName(accountName);
			theAccount.setBalance(accountBalance);

			if (persistAccount(theAccount)) {
				MainWindow.getInstance().getNavigationPanel().update();
				this.dispose();
				GuiUtils.info("AccountWindow." + persistMode.toString()
						+ ".Success");
			}
		} catch (BadNameException ex) {
			GuiUtils.warn("AccountWindow." + persistMode.toString()
					+ ".Failure.BadName");
		} catch (ParseException ex) {
			GuiUtils.warn("AccountWindow." + persistMode.toString()
					+ ".Failure.BadNumber");
		}
	}

	private boolean persistAccount(Account theAccount) {
		boolean success = false;
		try {
			if (persistMode == PersistMode.CreateAccount) {
				AccountFacadeDelegateFactory.getDelegate().createAccount(
						theAccount);
			} else {
				AccountFacadeDelegateFactory.getDelegate().updateAccount(
						theAccount);
			}
			success = true;
		} catch (InstanceNotFoundException ex) {
			GuiUtils.error("AccountWindow." + persistMode.toString()
					+ ".Failure.NotFound");
		} catch (InternalErrorException ex) {
			GuiUtils.error("AccountWindow." + persistMode.toString()
					+ ".Failure.Generic");
		}
		return success;
	}

	/**
	 * This method initializes cancelButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(MessageManager.getMessage("cancelButton"));
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelButtonActionPerformed(e);
				}
			});
		}
		return cancelButton;
	}

	protected void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

	/**
	 * This method initializes componentsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getComponentsPane() {
		if (componentsPane == null) {
			accountBalanceLabel = new JLabel();
			accountBalanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			accountBalanceLabel.setText(MessageManager
					.getMessage("AccountWindow.Balance"));
			accountBalanceLabel.setBounds(new Rectangle(0, 60, 89, 28));

			accountNameLabel = new JLabel();
			accountNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			accountNameLabel.setText(MessageManager
					.getMessage("AccountWindow.AccountName"));
			accountNameLabel.setBounds(new Rectangle(0, 20, 89, 28));

			componentsPane = new JPanel();
			componentsPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 20,
					20));
			componentsPane.setLayout(null);
			componentsPane.add(accountNameLabel, null);
			componentsPane.add(getAccountNameTextField(), null);
			componentsPane.add(accountBalanceLabel, null);
			componentsPane.add(getAccountBalanceTextField(), null);
		}
		return componentsPane;
	}

	private JLabel getIconLabel() {
		iconLabel = new JLabel();
		iconLabel.setIcon(IconManager.getIcon("toolbar_add_account"));
		iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		return iconLabel;
	}

	/**
	 * This method initializes accountNameTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAccountNameTextField() {
		if (accountNameTextField == null) {
			accountNameTextField = new JTextField();
			accountNameTextField.setText(theAccount.getName());
			accountNameTextField.setBounds(new Rectangle(98, 20, 172, 28));
		}
		return accountNameTextField;
	}

	/**
	 * This method initializes accountBalanceTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAccountBalanceTextField() {
		if (accountBalanceTextField == null) {
			accountBalanceTextField = new JTextField();
			accountBalanceTextField.setText(NumberFormat.getInstance().format(
					theAccount.getBalance()));
			accountBalanceTextField.setBounds(new Rectangle(98, 60, 172, 28));
		}
		return accountBalanceTextField;
	}
} // @jve:decl-index=0:visual-constraint="128,6"
