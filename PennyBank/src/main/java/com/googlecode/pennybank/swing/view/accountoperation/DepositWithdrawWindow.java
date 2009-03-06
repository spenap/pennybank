/**
 * AddAccountOperationWindow.java
 * 
 * 03/03/2009
 */
package com.googlecode.pennybank.swing.view.accountoperation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.ImageIcon;
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

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class DepositWithdrawWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel mainContentPane = null;
	private JPanel fieldsPane = null;
	private JPanel buttonsPane = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private Type type;
	private JLabel iconLabel = null;
	private JPanel componentsPane = null;
	private String title;
	private ImageIcon imageIcon;
	private JLabel amountLabel = null;
	private JTextField amountTextField = null;
	private JLabel dateLabel = null;
	private JTextField dateTextField = null;
	private JComboBox categoryComboBox = null;
	private JTextArea descriptionTextArea = null;
	private Account account;

	/**
	 * @param owner
	 * @param operatedAccount
	 */
	public DepositWithdrawWindow(Frame owner, Type type, Account operatedAccount) {
		super(owner);
		this.type = type;
		this.account = operatedAccount;
		initialize(owner);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(Frame owner) {
		this.setSize(420, 250);
		switch (type) {
		case WITHDRAW:
			title = MessageManager
					.getMessage("WithdrawFromAccountWindow.Title");
			break;
		case DEPOSIT:
			title = MessageManager.getMessage("AddToAccountWindow.Title");
		default:
			break;
		}
		this.setLocationRelativeTo(owner);
		this.setResizable(false);
		this.setTitle(title);
		this.setContentPane(getMainContentPane());
	}

	private JLabel getIconLabel() {
		iconLabel = new JLabel();
		imageIcon = IconManager.getIcon("toolbar_deposit");
		switch (type) {
		case WITHDRAW:
			imageIcon = IconManager.getIcon("toolbar_withdraw");
			break;
		case DEPOSIT:
			imageIcon = IconManager.getIcon("toolbar_deposit");
		default:
			break;
		}
		iconLabel.setIcon(imageIcon);
		iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		return iconLabel;
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
			mainContentPane.add(getButtonsPane(), BorderLayout.SOUTH);
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
			fieldsPane.add(getIconLabel(), BorderLayout.WEST);
			fieldsPane.add(getComponentsPane(), BorderLayout.CENTER);
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
			dateLabel = new JLabel();
			dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
			dateLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Date"));
			dateLabel.setBounds(new Rectangle(22, 50, 120, 30));
			amountLabel = new JLabel();
			amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			amountLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Amount"));
			amountLabel.setBounds(new Rectangle(22, 10, 120, 30));
			componentsPane = new JPanel();
			componentsPane.setLayout(null);
			componentsPane.add(amountLabel, null);
			componentsPane.add(getDescriptionTextArea(), null);
			componentsPane.add(dateLabel, null);
			componentsPane.add(getAmountTextField(), null);
			componentsPane.add(getCategoryComboBox(), null);
			componentsPane.add(getDateTextField(), null);
		}
		return componentsPane;
	}

	/**
	 * This method initializes amountTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAmountTextField() {
		if (amountTextField == null) {
			amountTextField = new JTextField();
			amountTextField.setBounds(new Rectangle(164, 50, 180, 30));
		}
		return amountTextField;
	}

	/**
	 * This method initializes dateTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDateTextField() {
		if (dateTextField == null) {
			dateTextField = new JTextField();
			dateTextField.setBounds(new Rectangle(164, 10, 180, 30));
		}
		return dateTextField;
	}

	/**
	 * This method initializes categoryComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCategoryComboBox() {
		if (categoryComboBox == null) {
			categoryComboBox = new JComboBox();
			categoryComboBox
					.addItem(MessageManager
							.getMessage("AccountOperationWindow.Category.Uncategorized"));
			for (Category category : App.getCategories()) {
				categoryComboBox.addItem(category.getName());
			}
			categoryComboBox.addItem(MessageManager
					.getMessage("AccountOperationWindow.Category.New"));
			categoryComboBox.setBounds(new Rectangle(30, 86, 310, 47));
			categoryComboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					categoryComboBoxItemChanged(e);
				}
			});
		}
		return categoryComboBox;
	}

	protected void categoryComboBoxItemChanged(ItemEvent e) {
		if ((e.getStateChange() == ItemEvent.SELECTED)
				&& (e.getItem().toString().equals(MessageManager
						.getMessage("AccountOperationWindow.Category.New")))) {
			System.out.println("Create new category");
			categoryComboBox.insertItemAt("New created category",
					categoryComboBox.getItemCount() - 1);
			categoryComboBox
					.setSelectedIndex(categoryComboBox.getItemCount() - 2);
		}
	}

	/**
	 * This method initializes descriptionTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getDescriptionTextArea() {
		if (descriptionTextArea == null) {
			descriptionTextArea = new JTextArea();
			descriptionTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setBounds(new Rectangle(30, 130, 310, 55));
		}
		return descriptionTextArea;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
