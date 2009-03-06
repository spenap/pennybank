/**
 * TransferWindow.java
 * 
 * 06/03/2009
 */
package com.googlecode.pennybank.swing.view.accountoperation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class TransferWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel mainContentPane = null;
	private Account account;
	private JPanel buttonsPane = null;
	private JPanel fieldsPane = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private JLabel iconLabel = null;
	private JPanel componentsPane = null;
	private JLabel amountLabel = null;
	private JTextArea descriptionTextArea = null;
	private JLabel dateLabel = null;
	private JTextField amountTextField = null;
	private JComboBox categoryComboBox = null;
	private JTextField dateTextField = null;
	private JLabel sourceAccountLabel = null;
	private JLabel destinyAccountLabel = null;
	private JComboBox sourceAccountComboBox = null;
	private JComboBox destinyAccountComboBox = null;
	private JLabel categoryLabel = null;

	/**
	 * @param owner
	 * @param operatedAccount
	 */
	public TransferWindow(Frame owner, Account operatedAccount) {
		super(owner);
		this.account = operatedAccount;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(721, 341);
		this.setResizable(false);
		this.setTitle(MessageManager
				.getMessage("TransferBetweenAccountsWindow.Title"));
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
			mainContentPane.add(getButtonsPane(), BorderLayout.SOUTH);
			mainContentPane.add(getFieldsPane(), BorderLayout.CENTER);
		}
		return mainContentPane;
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
	 * This method initializes fieldsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getFieldsPane() {
		if (fieldsPane == null) {

			fieldsPane = new JPanel();
			fieldsPane.setLayout(new BorderLayout());
			fieldsPane.add(getIconLabel(), BorderLayout.WEST);
			fieldsPane.add(getComponentsPane(), BorderLayout.CENTER);
		}
		return fieldsPane;
	}

	private JLabel getIconLabel() {
		iconLabel = new JLabel();
		iconLabel.setIcon(IconManager.getIcon("toolbar_transfer"));
		iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		return iconLabel;
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
		System.out.println(account);
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
			getRootPane().setDefaultButton(cancelButton);
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
			categoryLabel = new JLabel();
			categoryLabel.setBounds(new Rectangle(19, 134, 130, 30));
			categoryLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Category"));
			categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
			destinyAccountLabel = new JLabel();
			destinyAccountLabel.setBounds(new Rectangle(341, 22, 130, 36));
			destinyAccountLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.DestinyAccount"));
			destinyAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			sourceAccountLabel = new JLabel();
			sourceAccountLabel.setBounds(new Rectangle(17, 22, 130, 30));
			sourceAccountLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.SourceAccount"));
			sourceAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			dateLabel = new JLabel();
			dateLabel.setBounds(new Rectangle(341, 75, 130, 30));
			dateLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Date"));
			dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
			amountLabel = new JLabel();
			amountLabel.setBounds(new Rectangle(19, 78, 130, 30));
			amountLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Amount"));
			amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			componentsPane = new JPanel();
			componentsPane.setLayout(null);
			componentsPane.add(amountLabel, null);
			componentsPane.add(getDescriptionTextArea(), null);
			componentsPane.add(dateLabel, null);
			componentsPane.add(getAmountTextField(), null);
			componentsPane.add(getCategoryComboBox(), null);
			componentsPane.add(getDateTextField(), null);
			componentsPane.add(sourceAccountLabel, null);
			componentsPane.add(destinyAccountLabel, null);
			componentsPane.add(getSourceAccountCombobox(), null);
			componentsPane.add(getDestinyAccountComboBox(), null);
			componentsPane.add(categoryLabel, null);
		}
		return componentsPane;
	}

	/**
	 * This method initializes descriptionTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getDescriptionTextArea() {
		if (descriptionTextArea == null) {
			descriptionTextArea = new JTextArea();
			descriptionTextArea.setBounds(new Rectangle(20, 199, 628, 55));
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
		}
		return descriptionTextArea;
	}

	/**
	 * This method initializes amountTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAmountTextField() {
		if (amountTextField == null) {
			amountTextField = new JTextField();
			amountTextField.setBounds(new Rectangle(488, 74, 160, 30));
		}
		return amountTextField;
	}

	/**
	 * This method initializes categoryComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCategoryComboBox() {
		if (categoryComboBox == null) {
			categoryComboBox = new JComboBox();
			categoryComboBox.setBounds(new Rectangle(164, 129, 307, 47));
		}
		return categoryComboBox;
	}

	/**
	 * This method initializes dateTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDateTextField() {
		if (dateTextField == null) {
			dateTextField = new JTextField();
			dateTextField.setBounds(new Rectangle(164, 77, 160, 30));
		}
		return dateTextField;
	}

	/**
	 * This method initializes sourceAccountCombobox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getSourceAccountCombobox() {
		if (sourceAccountComboBox == null) {
			sourceAccountComboBox = new JComboBox();
			sourceAccountComboBox.setBounds(new Rectangle(164, 21, 160, 33));
		}
		return sourceAccountComboBox;
	}

	/**
	 * This method initializes destinyAccountComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDestinyAccountComboBox() {
		if (destinyAccountComboBox == null) {
			destinyAccountComboBox = new JComboBox();
			destinyAccountComboBox.setBounds(new Rectangle(488, 17, 160, 38));
		}
		return destinyAccountComboBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
