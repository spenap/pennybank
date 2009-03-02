/**
 * AddAccountOperationWindow.java
 * 
 * 01/03/2009
 */
package com.googlecode.pennybank.swing.view.accountoperation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * JDialog which allows the user to operate the account
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class AddAccountOperationWindow extends JDialog {

	private Type operationType;
	private Account account;
	private JLabel accountIcon;
	private JLabel amountLabel;
	private JTextField amountTextField;
	private JLabel commentLabel;
	private JTextArea commentTextArea;
	private JPanel buttonsPanel;
	private JButton cancelButton;
	private JPanel fieldsPanel;
	private JButton okButton;

	public AddAccountOperationWindow(JFrame parent, boolean modal, Type type,
			Account operatedAccount) {
		super(parent, modal);
		this.account = operatedAccount;
		this.operationType = type;
		initComponents();
	}

	private void initComponents() {
		accountIcon = new JLabel();
		buttonsPanel = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		fieldsPanel = new JPanel();
		amountLabel = new JLabel();
		amountTextField = new JTextField();
		commentLabel = new JLabel();
		commentTextArea = new JTextArea();

		String windowTitle = operationType == Type.DEPOSIT ? MessageManager
				.getMessage("AddToAccountWindow.Title") : MessageManager
				.getMessage("WithdrawFromAccountWindow.Title");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(windowTitle);
		setResizable(false);

		ImageIcon windowIcon = operationType == Type.DEPOSIT ? IconManager
				.getIcon("toolbar_deposit") : IconManager
				.getIcon("toolbar_withdraw");

		accountIcon.setIcon(windowIcon);
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

		amountLabel.setText(MessageManager
				.getMessage("AccountOperationWindow.Amount"));
		fieldsPanel.add(amountLabel);

		amountTextField.setMinimumSize(new Dimension(30, 28));
		amountTextField.setPreferredSize(new Dimension(100, 28));
		fieldsPanel.add(amountTextField);

		commentLabel.setText(MessageManager
				.getMessage("AccountOperationWindow.Comment"));
		fieldsPanel.add(commentLabel);

		fieldsPanel.add(commentTextArea);

		getContentPane().add(fieldsPanel, BorderLayout.CENTER);

		pack();
	}

	private void okButtonActionPerformed(ActionEvent evt) {
		try {
			AccountFacadeDelegate accountFacade = App.getAccountFacade();
			switch (operationType) {
			case DEPOSIT:
				accountFacade
						.addToAccount(account.getAccountId(), Double
								.parseDouble(amountTextField.getText()),
								commentTextArea.getText(), Calendar
										.getInstance(), null);
				break;
			case WITHDRAW:
				accountFacade
						.withdrawFromAccount(account.getAccountId(), Double
								.parseDouble(amountTextField.getText()),
								commentTextArea.getText(), Calendar
										.getInstance(), null);
				break;
			default:
				break;
			}
			this.dispose();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegativeAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cancelButtonActionPerformed(ActionEvent evt) {
		this.dispose();
	}
}
