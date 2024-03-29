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
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;

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
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.view.category.CategoriesComboBox;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.ResultWindow;

/**
 * @author spenap
 * 
 */
public class DepositWithdrawWindow extends JDialog implements ResultWindow {

	private static final long serialVersionUID = 1L;
	private JPanel mainContentPane = null;
	private JPanel fieldsPane = null;
	private JPanel buttonsPane = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private Type operationType;
	private JLabel iconLabel = null;
	private JPanel componentsPane = null;
	private String title = null;
	private ImageIcon imageIcon = null;
	private JLabel amountLabel = null;
	private JTextField amountTextField = null;
	private JLabel dateLabel = null;
	private JTextField dateTextField = null;
	private JComboBox categoryComboBox = null;
	private JTextArea descriptionTextArea = null;
	private JLabel descriptionLabel = null;
	private DateFormat dateFormat = null;
	private AccountOperation theOperation = null;
	private ResultType windowResult;

	public AccountOperation getAccountOperation() {
		return theOperation;
	}

	/**
	 * @param owner
	 * @param operatedAccount
	 */
	public DepositWithdrawWindow(Frame owner, Type type, Account operatedAccount) {
		super(owner);
		this.operationType = type;
		this.theOperation = new AccountOperation(operatedAccount, type, 0,
				Calendar.getInstance(), "");
		this.dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		initialize(owner);
	}

	public DepositWithdrawWindow(Frame owner, AccountOperation operation) {
		super(owner);
		this.operationType = operation.getType();
		this.theOperation = operation;
		this.dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		initialize(owner);
	}

	/**
	 * This method initializes amountTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAmountTextField() {
		if (amountTextField == null) {
			amountTextField = new JTextField();
			amountTextField.setText(NumberFormat.getInstance().format(
					theOperation.getAmount()));
			amountTextField.setBounds(new Rectangle(164, 1, 180, 30));
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
			flowLayout.setAlignment(FlowLayout.RIGHT);
			flowLayout.setAlignment(FlowLayout.RIGHT);
			flowLayout.setAlignment(FlowLayout.RIGHT);
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
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancelButtonActionPerformed(e);
				}
			});
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
			categoryComboBox = new CategoriesComboBox();
			Category theCategory = theOperation.getCategory();
			String categoryName = null;
			if (theCategory != null) {
				categoryName = theCategory.getName();
			} else {
				categoryName = MessageManager
						.getMessage("Category.Uncategorized");
			}
			categoryComboBox.setSelectedItem(categoryName);
			categoryComboBox.setBounds(new Rectangle(17, 71, 327, 47));
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
			descriptionLabel.setBounds(new Rectangle(20, 109, 130, 30));
			descriptionLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Description"));
			dateLabel = new JLabel();
			dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			dateLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Date"));
			dateLabel.setBounds(new Rectangle(20, 41, 130, 30));
			amountLabel = new JLabel();
			amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			amountLabel.setText(MessageManager
					.getMessage("AccountOperationWindow.Amount"));
			amountLabel.setBounds(new Rectangle(20, 1, 130, 30));
			componentsPane = new JPanel();
			componentsPane.setLayout(null);
			componentsPane.add(amountLabel, null);
			componentsPane.add(getDescriptionTextArea(), null);
			componentsPane.add(dateLabel, null);
			componentsPane.add(getAmountTextField(), null);
			componentsPane.add(getCategoryComboBox(), null);
			componentsPane.add(getDateTextField(), null);
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
			dateTextField.setText(dateFormat.format(theOperation.getDate()
					.getTime()));
			dateTextField.setBounds(new Rectangle(164, 41, 180, 30));
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
			descriptionTextArea.setText(theOperation.getComment());
			descriptionTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setBounds(new Rectangle(20, 142, 327, 55));
		}
		return descriptionTextArea;
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
		imageIcon = IconManager.getIcon("toolbar_deposit");
		switch (operationType) {
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

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(Frame owner) {
		this.setSize(420, 264);
		switch (operationType) {
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
		this.setModal(true);
		this.setContentPane(getMainContentPane());
	}

	protected void cancelButtonActionPerformed(ActionEvent e) {
		this.windowResult = ResultType.CANCEL;
		this.dispose();
	}

	protected void okButtonActionPerformed(ActionEvent e) {
		try {
			// Parse amount
			double amount = Double.parseDouble(getAmountTextField().getText());
			String comment = getDescriptionTextArea().getText();

			// Parse date
			Calendar operationDate = Calendar.getInstance();
			operationDate.setTime(dateFormat
					.parse(getDateTextField().getText()));

			// Get category
			Category category = getSelectedCategory();

			theOperation.setAmount(amount);
			theOperation.setDate(operationDate);
			theOperation.setCategory(category);
			theOperation.setComment(comment);

			this.windowResult = ResultType.OK;
			this.dispose();
		} catch (ParseException ex) {
			GuiUtils.warn("AccountOperationWindow.Operate.Failure.BadDate");
		} catch (NumberFormatException ex) {
			GuiUtils.warn("AccountOperationWindow.Operate.Failure.BadNumber");
		}
	}

	public ResultType getResult() {
		return windowResult;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
