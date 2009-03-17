package com.googlecode.pennybank.swing.view.importation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.swing.view.accountoperation.AccountOperationTableModel;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.TableSorter;

public class ShowImportationResultsWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextPane descriptionTextPane = null;
	private Account account;
	private List<Category> categoryList;
	private List<AccountOperation> operationList;
	private JTabbedPane importationResultsPane = null;
	private JPanel categoriesPanel = null;
	private JPanel operationsPanel = null;
	private JScrollPane operationsTableScrollPane = null;
	private JTable operationsTable = null;
	private JTree categoriesTree = null;
	private JPanel buttonsPane = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private JButton previousButton = null;
	private JButton nextButton = null;
	private JScrollPane categoriesScrollPane = null; // @jve:decl-index=0:visual-constraint="248,311"
	private Dimension windowDimension;
	private JPanel accountConfigurationPane = null; // @jve:decl-index=0:visual-constraint="718,103"
	private JLabel destinationUserLabel = null;
	private JLabel destinationAccountLabel = null;
	private JComboBox destinationUserComboBox = null;
	private JComboBox destinationAccountComboBox = null;
	private JTextField destinationAccountTextField = null; // @jve:decl-index=0:visual-constraint="931,41"
	private JPanel importationOptionsPane = null;
	private JPanel destinationAccountSelectionPane = null;
	private JRadioButton createNewAccountRadioButton = null;
	private JRadioButton useExistingAccountRadioButton = null;
	private ButtonGroup destinationAccountButtonGroup = null; // @jve:decl-index=0:
	private boolean useExistingAccount;
	private List<User> userList; // @jve:decl-index=0:

	/**
	 * @param owner
	 */
	public ShowImportationResultsWindow(Frame owner, Account account,
			List<Category> categoryList, List<AccountOperation> operationList) {
		super(owner);
		this.account = account;
		this.categoryList = categoryList;
		this.operationList = operationList;
		initialize(owner);
	}

	private TreeModel createTreeModel(List<Category> categoryList) {
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode();
		List<Category> rootCategories = getRootCategories(categoryList);

		for (Category rootCategory : rootCategories) {
			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
					rootCategory);
			treeNode.add(rootNode);
			populateTreeNode(rootNode, rootCategory.getChildCategories());
		}

		return new DefaultTreeModel(treeNode);
	}

	/**
	 * This method initializes accountConfigurationPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAccountConfigurationPane() {
		if (accountConfigurationPane == null) {
			destinationAccountLabel = new JLabel();
			destinationAccountLabel.setText(MessageManager
					.getMessage("Importation.Preview.DestinationAccount"));
			destinationAccountLabel.setBounds(new Rectangle(16, 89, 106, 27));
			destinationUserLabel = new JLabel();
			destinationUserLabel.setText(MessageManager
					.getMessage("Importation.Preview.DestinationUser"));
			destinationUserLabel.setBounds(new Rectangle(16, 31, 106, 27));
			accountConfigurationPane = new JPanel();
			accountConfigurationPane.setLayout(null);
			accountConfigurationPane.setSize(new Dimension(279, 147));
			accountConfigurationPane.add(destinationUserLabel, null);
			accountConfigurationPane.add(destinationAccountLabel, null);
			accountConfigurationPane.add(getDestinationUserComboBox(), null);
			accountConfigurationPane.add(getDestinationAccountComboBox(), null);
			accountConfigurationPane
					.add(getDestinationAccountTextField(), null);
			accountConfigurationPane.setPreferredSize(accountConfigurationPane
					.getSize());
		}
		return accountConfigurationPane;
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
			buttonsPane.add(getPreviousButton(), null);
			buttonsPane.add(getNextButton(), null);
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
			getRootPane().setDefaultButton(cancelButton);
			cancelButton.setText(MessageManager.getMessage("cancelButton"));
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelButtonActionPerformed(e);
				}
			});
		}
		return cancelButton;
	}

	/**
	 * This method initializes categoriesPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCategoriesPanel() {
		if (categoriesPanel == null) {
			categoriesPanel = new JPanel();
			categoriesPanel.setLayout(new BorderLayout());
			categoriesPanel.add(getCategoriesScrollPane(), BorderLayout.CENTER);
		}
		return categoriesPanel;
	}

	/**
	 * This method initializes categoriesScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getCategoriesScrollPane() {
		if (categoriesScrollPane == null) {
			categoriesScrollPane = new JScrollPane();
			categoriesScrollPane.setViewportView(getCategoriesTree());
		}
		return categoriesScrollPane;
	}

	/**
	 * This method initializes categoriesTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getCategoriesTree() {
		if (categoriesTree == null) {
			TreeModel treeModel = createTreeModel(categoryList);
			categoriesTree = new JTree(treeModel);
			categoriesTree.setRootVisible(false);
		}
		return categoriesTree;
	}

	/**
	 * This method initializes createNewAccountRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCreateNewAccountRadioButton() {
		if (createNewAccountRadioButton == null) {
			createNewAccountRadioButton = new JRadioButton();
			createNewAccountRadioButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					destinationAccountButtonGroupActionPerformed(e);
				}

			});
			destinationAccountButtonGroup.add(createNewAccountRadioButton);
			destinationAccountButtonGroup.setSelected(
					createNewAccountRadioButton.getModel(), true);
			createNewAccountRadioButton.setText(MessageManager
					.getMessage("Importation.Preview.CreateNew"));
		}
		return createNewAccountRadioButton;
	}

	/**
	 * This method initializes descriptionTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getDescriptionTextPane() {
		if (descriptionTextPane == null) {
			descriptionTextPane = new JTextPane();
			descriptionTextPane.setBorder(new EmptyBorder(20, 20, 20, 20));
			descriptionTextPane.setEditable(false);
			descriptionTextPane.setBackground(jContentPane.getBackground());
			descriptionTextPane.setText(MessageManager
					.getMessage("Importation.Preview")
					+ " " + account.getName());
		}
		return descriptionTextPane;
	}

	/**
	 * This method initializes destinationAccountComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDestinationAccountComboBox() {
		if (destinationAccountComboBox == null) {
			destinationAccountComboBox = new JComboBox();
			destinationAccountComboBox
					.setBounds(new Rectangle(138, 89, 123, 27));
		}
		return destinationAccountComboBox;
	}

	/**
	 * This method initializes destinationAccountSelectionPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getDestinationAccountSelectionPane() {
		if (destinationAccountSelectionPane == null) {
			destinationAccountSelectionPane = new JPanel();
			destinationAccountSelectionPane.setLayout(new FlowLayout());
			destinationAccountSelectionPane.add(
					getCreateNewAccountRadioButton(), null);
			destinationAccountSelectionPane.add(
					getUseExistingAccountRadioButton(), null);
			if (userList.size() == 1
					&& userList.get(0).getAccounts().size() == 0) {
				getUseExistingAccountRadioButton().setEnabled(false);
			}
		}
		return destinationAccountSelectionPane;
	}

	/**
	 * This method initializes destinationAccountTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDestinationAccountTextField() {
		if (destinationAccountTextField == null) {
			destinationAccountTextField = new JTextField();
			destinationAccountTextField.setText(account.getName());
			destinationAccountTextField.setBounds(new Rectangle(138, 89, 123,
					27));
		}
		return destinationAccountTextField;
	}

	/**
	 * This method initializes destinationUserComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDestinationUserComboBox() {
		if (destinationUserComboBox == null) {
			destinationUserComboBox = new JComboBox();
			for (User user : userList) {
				destinationUserComboBox.addItem(user);
			}
			destinationUserComboBox.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					destinationUserComboBoxActionPerformed(e);
				}
			});
			destinationUserComboBox.setBounds(new Rectangle(138, 31, 123, 27));
		}
		return destinationUserComboBox;
	}

	/**
	 * This method initializes importationOptionsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getImportationOptionsPane() {
		if (importationOptionsPane == null) {
			importationOptionsPane = new JPanel();
			importationOptionsPane.setLayout(new BorderLayout());
			importationOptionsPane.add(getDescriptionTextPane(),
					BorderLayout.CENTER);
			importationOptionsPane.add(getDestinationAccountSelectionPane(),
					BorderLayout.SOUTH);
		}
		return importationOptionsPane;
	}

	/**
	 * This method initializes importationResultsPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getImportationResultsPane() {
		if (importationResultsPane == null) {
			importationResultsPane = new JTabbedPane();
			importationResultsPane.addTab(MessageManager
					.getMessage("Importation.Categories"), null,
					getCategoriesPanel(), null);
			importationResultsPane.addTab(MessageManager
					.getMessage("Importation.Operations"), null,
					getOperationsPanel(), null);
		}
		return importationResultsPane;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getImportationResultsPane(), BorderLayout.CENTER);
			jContentPane.add(getButtonsPane(), BorderLayout.SOUTH);
			jContentPane.add(getImportationOptionsPane(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes nextButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNextButton() {
		if (nextButton == null) {
			nextButton = new JButton();
			nextButton.setText(MessageManager.getMessage("continueButton"));
			nextButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					nextButtonActionPerformed(e);
				}
			});
		}
		return nextButton;
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
			okButton.setVisible(false);
			okButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					okButtonActionPerformed(e);
				}

			});
		}
		return okButton;
	}

	/**
	 * This method initializes operationsPanel
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JPanel getOperationsPanel() {
		if (operationsPanel == null) {
			operationsPanel = new JPanel();
			operationsPanel.setLayout(new BorderLayout());
			operationsPanel.add(getOperationsTableScrollPane(),
					BorderLayout.CENTER);
		}
		return operationsPanel;
	}

	/**
	 * This method initializes operationsTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getOperationsTable() {
		if (operationsTable == null) {
			AccountOperationTableModel tableModel = new AccountOperationTableModel();
			tableModel.setContent(operationList);
			TableSorter tableSorter = new TableSorter(tableModel);
			operationsTable = new JTable(tableSorter);
			tableSorter.setTableHeader(operationsTable.getTableHeader());
		}
		return operationsTable;
	}

	/**
	 * This method initializes operationsTableScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getOperationsTableScrollPane() {
		if (operationsTableScrollPane == null) {
			operationsTableScrollPane = new JScrollPane();
			operationsTableScrollPane.setViewportView(getOperationsTable());
		}
		return operationsTableScrollPane;
	}

	/**
	 * This method initializes previousButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPreviousButton() {
		if (previousButton == null) {
			previousButton = new JButton();
			previousButton.setText(MessageManager.getMessage("backButton"));
			previousButton.setVisible(false);
			previousButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							previousButtonActionPerformed(e);
						}
					});
		}
		return previousButton;
	}

	private List<Category> getRootCategories(List<Category> categoryList) {
		List<Category> rootCategories = new ArrayList<Category>();
		for (Category category : categoryList) {
			if (category.getParentCategory() == null)
				rootCategories.add(category);
		}
		return rootCategories;
	}

	/**
	 * This method initializes useExistingAccountRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getUseExistingAccountRadioButton() {
		if (useExistingAccountRadioButton == null) {
			useExistingAccountRadioButton = new JRadioButton();
			useExistingAccountRadioButton
					.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							destinationAccountButtonGroupActionPerformed(e);
						}

					});
			destinationAccountButtonGroup.add(useExistingAccountRadioButton);
			destinationAccountButtonGroup.setSelected(
					useExistingAccountRadioButton.getModel(), false);
			useExistingAccountRadioButton.setText(MessageManager
					.getMessage("Importation.Preview.UseExisting"));
		}
		return useExistingAccountRadioButton;
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
	private void initialize(Frame owner) {
		this.userList = getUserList();
		this.setSize(549, 292);
		this.setModal(false);
		this.setTitle(MessageManager.getMessage("Importation.Preview.Title"));
		this.destinationAccountButtonGroup = new ButtonGroup();
		this.setContentPane(getJContentPane());
		this.setLocationRelativeTo(owner);
	}

	private void populateAccountComboBox(User theUser) {
		for (Account account : theUser.getAccounts()) {
			getDestinationAccountComboBox().addItem(account);
		}
	}

	private void populateTreeNode(DefaultMutableTreeNode rootNode,
			List<Category> childCategories) {
		for (Category category : childCategories) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(category);
			rootNode.add(node);
			populateTreeNode(node, category.getChildCategories());
		}
	}

	protected void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

	protected void destinationAccountButtonGroupActionPerformed(ActionEvent e) {
		this.useExistingAccount = destinationAccountButtonGroup
				.isSelected(getUseExistingAccountRadioButton().getModel());
	}

	protected void destinationUserComboBoxActionPerformed(ActionEvent e) {
		User theUser = (User) getDestinationUserComboBox().getSelectedItem();
		populateAccountComboBox(theUser);
	}

	protected void nextButtonActionPerformed(ActionEvent e) {
		// Store previous dimension
		windowDimension = this.getSize();

		// Set panels in content panel
		jContentPane.remove(getImportationResultsPane());
		jContentPane.add(getAccountConfigurationPane(), BorderLayout.CENTER);

		// Set panels visibility
		getImportationResultsPane().setVisible(false);
		getDestinationAccountSelectionPane().setVisible(false);
		getAccountConfigurationPane().setVisible(true);

		// Set destination account component visibility
		getDestinationAccountTextField().setVisible(!useExistingAccount);
		getDestinationAccountComboBox().setVisible(useExistingAccount);

		// Set buttons visibility
		getNextButton().setVisible(false);
		getPreviousButton().setVisible(true);
		getOkButton().setVisible(true);

		this.pack();
	}

	protected void okButtonActionPerformed(ActionEvent e) {
		try {
			User destinationUser = (User) getDestinationUserComboBox()
					.getSelectedItem();
			Account account = null;

			AccountFacadeDelegate accountFacade = AccountFacadeDelegateFactory
					.getDelegate();
			if (useExistingAccount) {
				account = (Account) getDestinationAccountComboBox()
						.getSelectedItem();
			} else {
				account = new Account(destinationUser, 0.0,
						getDestinationAccountTextField().getText());
				account = accountFacade.createAccount(account);
			}

			for (Category category : categoryList) {
				App.getCategories().add(accountFacade.createCategory(category));
			}

			for (AccountOperation operation : operationList) {
				if (operation.getType() == Type.DEPOSIT) {
					accountFacade.addToAccount(account.getAccountId(),
							operation.getAmount(), operation.getComment(),
							operation.getDate(), operation.getCategory());
				} else {
					accountFacade.withdrawFromAccount(account.getAccountId(),
							operation.getAmount(), operation.getComment(),
							operation.getDate(), operation.getCategory());
				}
			}

			MainWindow mainWindow = MainWindow.getInstance();
			mainWindow.getNavigationPanel().update();
			mainWindow.getContentPanel().showAccountOperations(account);
			this.dispose();
			GuiUtils.info("Importation.Success");
		} catch (InternalErrorException ex) {
			GuiUtils.error("Importation.Failure");
		} catch (InstanceNotFoundException ex) {
			GuiUtils.error("Importation.Failure");
		} catch (NegativeAmountException ex) {
			GuiUtils.error("Importation.Failure");
		}
	}

	protected void previousButtonActionPerformed(ActionEvent e) {

		// Set panels in content pane
		jContentPane.add(getImportationResultsPane(), BorderLayout.CENTER);
		jContentPane.remove(getAccountConfigurationPane());

		// Set panels visibility
		getImportationResultsPane().setVisible(true);
		getAccountConfigurationPane().setVisible(false);
		getDestinationAccountSelectionPane().setVisible(true);

		// Set buttons visibility
		getOkButton().setVisible(false);
		getPreviousButton().setVisible(false);
		getNextButton().setVisible(true);

		this.setSize(windowDimension);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
