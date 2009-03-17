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
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.swing.controller.category.AddCategoryListener;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class TransferWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel mainContentPane = null;
	private Account sourceAccount;
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
	private JLabel sourceUserLabel = null;
	private JLabel destinyAccountLabel = null;
	private JComboBox destinationUserComboBox = null;
	private JComboBox destinationAccountComboBox = null;
	private JLabel categoryLabel = null;
	private JLabel descriptionLabel = null;
	private DateFormat dateFormat;
	private List<User> userList;

	/**
	 * @param owner
	 * @param operatedAccount
	 */
	public TransferWindow(Frame owner, Account operatedAccount) {
		super(owner);
		this.sourceAccount = operatedAccount;
		initialize();
	}

	/**
	 * This method initializes amountTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAmountTextField() {
		if (amountTextField == null) {
			amountTextField = new JTextField();
			amountTextField.setBounds(new Rectangle(155, 111, 160, 30));
		}
		return amountTextField;
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

	/**
	 * This method initializes categoryComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCategoryComboBox() {
		if (categoryComboBox == null) {
			categoryComboBox = new JComboBox();
			categoryComboBox.setBounds(new Rectangle(155, 201, 160, 30));
			categoryComboBox.addItem(MessageManager
					.getMessage("Category.Uncategorized"));
			for (Category category : App.getCategories()) {
				categoryComboBox.addItem(category.getName());
			}
			categoryComboBox.addItem(MessageManager.getMessage("Category.New"));
			categoryComboBox.addItemListener(new AddCategoryListener(
					categoryComboBox));
		}
		return categoryComboBox;
	}

	/**
	 * This method initializes componentsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getComponentsPane() {
		if (componentsPane == null) {
			descriptionLabel = new JLabel();
			descriptionLabel.setBounds(new Rectangle(14, 246, 130, 30));
			descriptionLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Description"));
			descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
			categoryLabel = new JLabel();
			categoryLabel.setBounds(new Rectangle(14, 201, 130, 30));
			categoryLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Category"));
			categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			destinyAccountLabel = new JLabel();
			destinyAccountLabel.setBounds(new Rectangle(14, 60, 130, 30));
			destinyAccountLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.DestinationAccount"));
			destinyAccountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			sourceUserLabel = new JLabel();
			sourceUserLabel.setBounds(new Rectangle(14, 15, 130, 30));
			sourceUserLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.DestinationUser"));
			sourceUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			dateLabel = new JLabel();
			dateLabel.setBounds(new Rectangle(14, 156, 130, 30));
			dateLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Date"));
			dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			amountLabel = new JLabel();
			amountLabel.setBounds(new Rectangle(14, 111, 130, 30));
			amountLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Amount"));
			amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			componentsPane = new JPanel();
			componentsPane.setLayout(null);
			componentsPane.add(amountLabel, null);
			componentsPane.add(getDescriptionTextArea(), null);
			componentsPane.add(dateLabel, null);
			componentsPane.add(getAmountTextField(), null);
			componentsPane.add(getCategoryComboBox(), null);
			componentsPane.add(getDateTextField(), null);
			componentsPane.add(sourceUserLabel, null);
			componentsPane.add(destinyAccountLabel, null);
			componentsPane.add(getDestinationUserCombobox(), null);
			componentsPane.add(getDestinationAccountComboBox(), null);
			componentsPane.add(categoryLabel, null);
			componentsPane.add(descriptionLabel, null);
		}
		return componentsPane;
	}

	/**
	 * This method initializes dateTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDateTextField() {
		if (dateTextField == null) {
			dateTextField = new JTextField();
			dateTextField.setText(dateFormat.format(Calendar.getInstance()
					.getTime()));
			dateTextField.setBounds(new Rectangle(155, 156, 160, 30));
		}
		return dateTextField;
	}

	/**
	 * This method initializes descriptionTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getDescriptionTextArea() {
		if (descriptionTextArea == null) {
			descriptionTextArea = new JTextArea();
			descriptionTextArea.setBounds(new Rectangle(14, 280, 299, 81));
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
		}
		return descriptionTextArea;
	}

	/**
	 * This method initializes destinyAccountComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDestinationAccountComboBox() {
		if (destinationAccountComboBox == null) {
			destinationAccountComboBox = new JComboBox();
			User selectedUser = (User) getDestinationUserCombobox()
					.getSelectedItem();
			populateDestinationAccounts(selectedUser);
			destinationAccountComboBox
					.setBounds(new Rectangle(155, 60, 160, 30));
		}
		return destinationAccountComboBox;
	}

	/**
	 * This method initializes sourceAccountCombobox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDestinationUserCombobox() {
		if (destinationUserComboBox == null) {
			destinationUserComboBox = new JComboBox();
			destinationUserComboBox.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					userComboBoxActionPerformed();
				}

			});
			for (User user : userList) {
				destinationUserComboBox.addItem(user);
			}
			destinationUserComboBox.setBounds(new Rectangle(155, 15, 160, 30));
		}
		return destinationUserComboBox;
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

	private Category getSelectedCategory() {
		Category selectedCategory = null;
		String selectedCategoryName = getCategoryComboBox().getSelectedItem()
				.toString();
		for (Category category : App.getCategories()) {
			if (category.getName().equals(selectedCategoryName)) {
				selectedCategory = category;
				break;
			}
		}

		return selectedCategory;
	}

	private List<User> getUserList() {
		List<User> userList = null;
		try {
			userList = UserFacadeDelegateFactory.getDelegate().findUsers();
		} catch (InternalErrorException e) {
			userList = new ArrayList<User>();
		}
		return userList;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(382, 438);
		this.setResizable(false);
		this.setTitle(MessageManager
				.getMessage("TransferBetweenAccountsWindow.Title"));
		this.dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		this.userList = getUserList();
		this.setModal(true);
		this.setContentPane(getMainContentPane());
	}

	protected void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

	protected void okButtonActionPerformed(ActionEvent e) {
		try {
			Account destinationAccount = (Account) getDestinationAccountComboBox()
					.getSelectedItem();
			double amount = Double.parseDouble(getAmountTextField().getText());
			Calendar operationDate = Calendar.getInstance();
			operationDate.setTime(dateFormat
					.parse(getDateTextField().getText()));
			Category operationCategory = getSelectedCategory();
			String comment = getDescriptionTextArea().getText();
			AccountFacadeDelegateFactory.getDelegate().transfer(
					sourceAccount.getAccountId(),
					destinationAccount.getAccountId(), amount, comment,
					operationDate, operationCategory);
			MainWindow.getInstance().getContentPanel().showAccountOperations(
					sourceAccount);
			this.dispose();
			GuiUtils.info("AccountOperationWindow.Operate.Success");
		} catch (ParseException ex) {
			GuiUtils.warn("AccountOperationWindow.Operate.Failure.BadDate");
		} catch (NumberFormatException ex) {
			GuiUtils.warn("AccountOperationWindow.Operate.Failure.BadNumber");
		} catch (InstanceNotFoundException ex) {
			GuiUtils.error("AccountOperationWindow.Operate.Failure.NotFound");
		} catch (NegativeAmountException ex) {
			GuiUtils.warn("AccountOperationWindow.Operate.Failure.Negative");
		} catch (InternalErrorException ex) {
			GuiUtils.error("AccountOperationWindow.Operate.Failure.Generic");
		}
	}

	protected void userComboBoxActionPerformed() {

		User selectedUser = (User) destinationUserComboBox.getSelectedItem();

		populateDestinationAccounts(selectedUser);
	}

	private void populateDestinationAccounts(User selectedUser) {
		if (destinationAccountComboBox == null)
			return;
		destinationAccountComboBox.removeAllItems();
		for (Account account : selectedUser.getAccounts()) {
			if (!sourceAccount.equals(account))
				destinationAccountComboBox.addItem(account);
		}
		// The ok button is disabled if there are no valid accounts
		// for the user
		okButton.setEnabled(destinationAccountComboBox.getItemCount() != 0);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
