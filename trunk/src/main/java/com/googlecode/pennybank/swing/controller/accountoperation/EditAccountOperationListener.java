/**
 * 
 */
package com.googlecode.pennybank.swing.controller.accountoperation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.accountoperation.DepositWithdrawWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;

/**
 * @author spenap
 * 
 */
public class EditAccountOperationListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		List<AccountOperation> selectedOperations = getSelectedOperations();
		// TODO Edit the account operation
		if (selectedOperations.size() == 1) {
			AccountOperation operation = selectedOperations.get(0);
			DepositWithdrawWindow window = new DepositWithdrawWindow(MainWindow
					.getInstance(), operation);
			window.setVisible(true);
		}
	}

	private List<AccountOperation> getSelectedOperations() {
		return MainWindow.getInstance().getContentPanel().getOperationsTable()
				.getSelectedAccountOperations();
	}
}
