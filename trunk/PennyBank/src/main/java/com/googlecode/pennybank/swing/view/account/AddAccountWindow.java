/*
 * AddAccountWindow.java
 *
 * Created on 21-feb-2009, 1:10:27
 */
package com.googlecode.pennybank.swing.view.account;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * JDialog which allows the user to create a new account
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class AddAccountWindow extends JDialog {

	private JLabel accountIcon;
	private JLabel accountNameLabel;
	private JTextField accountNameTextField;
	private JLabel balanceLabel;
	private JTextField balanceTextField;
	private JPanel buttonsPanel;
	private JButton cancelButton;
	private JPanel fieldsPanel;
	private JButton okButton;

	/**
	 * Creates a new AddAccountWindow with the specified parameters
	 * 
	 * @param parent
	 *            The parent JFrame
	 * @param modal
	 *            Boolean value indicating whether this is modal or not
	 */
	public AddAccountWindow(JFrame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	private void initComponents() {

		accountIcon = new JLabel();
		buttonsPanel = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		fieldsPanel = new JPanel();
		accountNameLabel = new JLabel();
		accountNameTextField = new JTextField();
		balanceLabel = new JLabel();
		balanceTextField = new JTextField();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(MessageManager.getMessage("AddAccountWindow.Title"));
		setResizable(false);

		accountIcon.setIcon(IconManager.getIcon("toolbar_add_account"));
		accountIcon.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(accountIcon, BorderLayout.WEST);

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		okButton.setText(MessageManager.getMessage("okButton"));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});
		buttonsPanel.add(okButton);

		cancelButton.setText(MessageManager.getMessage("cancelButton"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
		buttonsPanel.add(cancelButton);

		getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);

		fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		fieldsPanel.setLayout(new GridLayout(2, 2, 2, 0));

		accountNameLabel.setText(MessageManager
				.getMessage("AccountWindow.AccountName"));
		fieldsPanel.add(accountNameLabel);

		accountNameTextField.setMinimumSize(new Dimension(30, 28));
		accountNameTextField.setPreferredSize(new Dimension(100, 28));
		fieldsPanel.add(accountNameTextField);

		balanceLabel
				.setText(MessageManager.getMessage("AccountWindow.Balance"));
		fieldsPanel.add(balanceLabel);

		balanceTextField.setMinimumSize(new Dimension(30, 28));
		balanceTextField.setPreferredSize(new Dimension(100, 28));
		fieldsPanel.add(balanceTextField);

		getContentPane().add(fieldsPanel, BorderLayout.CENTER);

		pack();
	}

	private void cancelButtonActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	private void okButtonActionPerformed(ActionEvent evt) {
		Account theAccount = new Account(App.getCurrentUser(), Double
				.parseDouble(balanceTextField.getText()), accountNameTextField
				.getText());
		try {

			AccountFacadeDelegate accountFacade = AccountFacadeDelegateFactory
					.getDelegate();
			theAccount = accountFacade.createAccount(theAccount);
		} catch (InstanceNotFoundException ex) {
			Logger.getLogger(AddAccountWindow.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (InternalErrorException ex) {
			Logger.getLogger(AddAccountWindow.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		MainWindow.getInstance().getNavigationPanel().addAccount(theAccount);
		this.dispose();
	}

}
