/*
 * Created on 21-feb-2009, 13:50:47
 */
package com.googlecode.pennybank.swing.view.main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.vo.Block;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * JPanel showing the Main Content for the application
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainContentPanel extends JPanel {

	private JScrollPane accountOperationsScrollPane;
	private JTable accountOperationsTable;

	/**
	 * Creates a new Main Content Panel
	 */
	public MainContentPanel() {
		initComponents();
	}

	private TableModel getAccountOperationsTableModel() {

		DefaultTableModel accountOperationsTableModel = new DefaultTableModel();
		accountOperationsTableModel.addColumn(MessageManager
				.getMessage("AccountOperationTable.Type"));
		accountOperationsTableModel.addColumn(MessageManager
				.getMessage("AccountOperationTable.Amount"));
		accountOperationsTableModel.addColumn(MessageManager
				.getMessage("AccountOperationTable.Date"));
		accountOperationsTableModel.addColumn(MessageManager
				.getMessage("AccountOperationTable.Comment"));
		accountOperationsTableModel.addColumn(MessageManager
				.getMessage("AccountOperationTable.Category"));

		return accountOperationsTableModel;
	}

	private void initComponents() {

		accountOperationsScrollPane = new JScrollPane();
		accountOperationsTable = new JTable();

		setLayout(new java.awt.BorderLayout());

		accountOperationsTable.setModel(getAccountOperationsTableModel());
		accountOperationsScrollPane.setViewportView(accountOperationsTable);

		add(accountOperationsScrollPane, java.awt.BorderLayout.CENTER);
	}

	public void setAccountOperations(
			Block<AccountOperation> block) {

		DefaultTableModel tableModel = (DefaultTableModel) accountOperationsTable
				.getModel();

		tableModel.setRowCount(block.getContents().size());

		for (int i = 0; i < block.getContents().size(); i++) {
			AccountOperation operationsBlock = block
					.getContents().get(i);
			// Type
			accountOperationsTable.setValueAt(operationsBlock.getType(),
					i, 0);

			// Amount
			accountOperationsTable.setValueAt(operationsBlock.getAmount(),
					i, 1);

			// Date
			accountOperationsTable.setValueAt(operationsBlock.getDate()
					.getTime().toString(), i, 2);

			// Comment
			accountOperationsTable.setValueAt(
					operationsBlock.getComment(), i, 3);

			// Categories
			accountOperationsTable.setValueAt(operationsBlock
					.getCategory(), i, 4);
		}
	}
}
