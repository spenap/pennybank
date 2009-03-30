/*
 * Created on 21-feb-2009, 13:50:47
 */
package com.googlecode.pennybank.swing.view.main;

import java.awt.BorderLayout;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.vo.Block;
import com.googlecode.pennybank.swing.view.accountoperation.AccountOperationComparator;
import com.googlecode.pennybank.swing.view.accountoperation.AccountOperationTableFormat;
import com.googlecode.pennybank.swing.view.accountoperation.AccountOperationTextFilterator;
import com.googlecode.pennybank.swing.view.util.AccountOperationTableRenderer;
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
	private SortedList<AccountOperation> accountOperationList;

	/**
	 * Creates a new Main Content Panel
	 */
	public MainContentPanel() {
		initComponents();
	}

	public void setAccountOperations(Block<AccountOperation> block) {
		accountOperationList.clear();
		accountOperationList.addAll(block.getContents());
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

			MainWindow.getInstance().getStatusBar().setText(
					selectedAccount.getName()
							+ ": "
							+ count.intValue()
							+ " "
							+ MessageManager.getMessage("StatusBar.Movements")
							+ " - "
							+ MessageManager.getMessage("StatusBar.Balance")
							+ ": "
							+ NumberFormat.getCurrencyInstance().format(
									selectedAccount.getBalance()));

		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initComponents() {

		accountOperationsScrollPane = null;
		accountOperationList = new SortedList<AccountOperation>(
				new BasicEventList<AccountOperation>(),
				new AccountOperationComparator());

		MatcherEditor<AccountOperation> textMatcherEditor = new TextComponentMatcherEditor<AccountOperation>(
				MainToolBar.getSearchField(),
				new AccountOperationTextFilterator());
		FilterList<AccountOperation> textFilteredIssues = new FilterList<AccountOperation>(
				accountOperationList, textMatcherEditor);
		EventTableModel<AccountOperation> accountOperationTableModel = new EventTableModel<AccountOperation>(
				textFilteredIssues, new AccountOperationTableFormat());

		setLayout(new BorderLayout());
		accountOperationsScrollPane = MacWidgetFactory
				.wrapITunesTableInJScrollPane(getTable(accountOperationTableModel));

		add(accountOperationsScrollPane, java.awt.BorderLayout.CENTER);
	}

	private JTable getTable(
			EventTableModel<AccountOperation> accountOperationTableModel) {
		if (accountOperationsTable == null) {
			accountOperationsTable = MacWidgetFactory
					.createITunesTable(accountOperationTableModel);

			TableComparatorChooser.install(accountOperationsTable,
					accountOperationList,
					TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

			accountOperationsTable.setDefaultRenderer(Date.class,
					new AccountOperationTableRenderer());
			accountOperationsTable.setDefaultRenderer(Double.class,
					new AccountOperationTableRenderer());
		}
		return accountOperationsTable;
	}
}
