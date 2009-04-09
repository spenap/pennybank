/*
 * Created on 21-feb-2009, 13:50:47
 */
package com.googlecode.pennybank.swing.view.main;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.vo.Block;
import com.googlecode.pennybank.swing.view.accountoperationtable.AccountOperationTable;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * JPanel showing the Main Content for the application
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainContentPanel extends JPanel {

	private JScrollPane accountOperationsScrollPane = null;
	private AccountOperationTable operationsTable = null;

	/**
	 * Creates a new Main Content Panel
	 */
	public MainContentPanel() {
		initComponents();
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
			operationsTable.setAccountOperations(operations.getContents());

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

	public AccountOperationTable getOperationsTable() {
		return operationsTable;
	}

	private void initComponents() {

		setLayout(new BorderLayout());
		operationsTable = new AccountOperationTable();
		JTable theTable = operationsTable.getJTable();
		theTable.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				JMenuBar menu = MainWindow.getInstance().getMainMenuBar();
				((MainMenuBar) menu).setOperationEnabled(true);
			}

			public void focusLost(FocusEvent e) {
				JMenuBar menu = MainWindow.getInstance().getMainMenuBar();
				((MainMenuBar) menu).setOperationEnabled(false);
			}

		});
		accountOperationsScrollPane = MacWidgetFactory
				.wrapITunesTableInJScrollPane(theTable);

		add(accountOperationsScrollPane, BorderLayout.CENTER);
	}
}
