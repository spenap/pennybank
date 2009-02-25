/*
 * Created on 21-feb-2009, 13:50:47
 */
package com.googlecode.pennybank.swing.view.main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.googlecode.pennybank.model.accountfacade.vo.AccountOperationInfo;
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
			Block<AccountOperationInfo> accountOperationBlocks) {

		DefaultTableModel tableModel = (DefaultTableModel) accountOperationsTable
				.getModel();

		tableModel.setRowCount(accountOperationBlocks.getContents().size());

		for (int i = 0; i < accountOperationBlocks.getContents().size(); i++) {
			AccountOperationInfo accountOperationInfo = accountOperationBlocks
					.getContents().get(i);
			// Type
			accountOperationsTable.setValueAt(accountOperationInfo.getType(),
					i, 0);

			// Amount
			accountOperationsTable.setValueAt(accountOperationInfo.getAmount(),
					i, 1);

			// Date
			accountOperationsTable.setValueAt(accountOperationInfo.getDate()
					.getTime().toString(), i, 2);

			// Comment
			accountOperationsTable.setValueAt(
					accountOperationInfo.getComment(), i, 3);

			// Categories
			accountOperationsTable.setValueAt(accountOperationInfo
					.getCategory(), i, 4);
		}
	}
}
