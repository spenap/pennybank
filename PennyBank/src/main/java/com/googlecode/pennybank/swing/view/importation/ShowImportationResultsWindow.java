package com.googlecode.pennybank.swing.view.importation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.view.util.MessageManager;

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

	private TableModel createTableModel(List<AccountOperation> operationList) {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeModel createTreeModel(List<Category> categoryList2) {
		// TODO Auto-generated method stub
		return null;
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
			categoriesPanel.add(getCategoriesTree(), BorderLayout.CENTER);
		}
		return categoriesPanel;
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
		}
		return categoriesTree;
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
			jContentPane.add(getDescriptionTextPane(), BorderLayout.NORTH);
			jContentPane.add(getImportationResultsPane(), BorderLayout.CENTER);
			jContentPane.add(getButtonsPane(), BorderLayout.SOUTH);
		}
		return jContentPane;
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
			TableModel tableModel = createTableModel(operationList);
			operationsTable = new JTable(tableModel);
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
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(Frame owner) {
		this.setSize(549, 292);
		this.setResizable(false);
		this.setTitle(MessageManager.getMessage("Importation.Results"));
		this.setContentPane(getJContentPane());
		this.setLocationRelativeTo(owner);
	}

	protected void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
