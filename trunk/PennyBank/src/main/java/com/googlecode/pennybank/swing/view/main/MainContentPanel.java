/*
 * Created on 21-feb-2009, 13:50:47
 */
package com.googlecode.pennybank.swing.view.main;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.vo.Block;
import com.googlecode.pennybank.swing.view.accountoperation.AccountOperationTableModel;
import com.googlecode.pennybank.swing.view.util.CustomDateRenderer;
import com.googlecode.pennybank.swing.view.util.TableSorter;

/**
 * JPanel showing the Main Content for the application
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainContentPanel extends JPanel {

	private JScrollPane accountOperationsScrollPane;
	private JTable accountOperationsTable;
	private AccountOperationTableModel tableModel;

	/**
	 * Creates a new Main Content Panel
	 */
	public MainContentPanel() {
		initComponents();
	}

	public void setAccountOperations(Block<AccountOperation> block) {
		tableModel.setContent(block.getContents());
	}

	public void showAccountOperations(Account selectedAccount) {
		try {
			int startIndex = 0;
			Long count = AccountFacadeDelegateFactory.getDelegate()
					.getOperationsCount(selectedAccount.getAccountId());
			Block<AccountOperation> operations = AccountFacadeDelegateFactory
					.getDelegate().findAccountOperations(
							selectedAccount.getAccountId(), startIndex,
							count.intValue());
			setAccountOperations(operations);
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initComponents() {

		accountOperationsScrollPane = new JScrollPane();
		tableModel = new AccountOperationTableModel();
		TableSorter tableSorter = new TableSorter(tableModel);
		accountOperationsTable = new JTable(tableSorter);
		accountOperationsTable.setDefaultRenderer(Date.class,
				new CustomDateRenderer());
		tableSorter.setTableHeader(accountOperationsTable.getTableHeader());

		setLayout(new BorderLayout());
		accountOperationsScrollPane.setViewportView(accountOperationsTable);

		add(accountOperationsScrollPane, java.awt.BorderLayout.CENTER);
	}
}
