/**
 * 
 */
package com.googlecode.pennybank.swing.controller.accountoperation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.controller.actions.EditAccountOperationAction;
import com.googlecode.pennybank.swing.view.accountoperation.DepositWithdrawWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

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
		if (selectedOperations.size() == 1) {
			AccountOperation operation = selectedOperations.get(0);
			AccountOperation clonedOperation = new AccountOperation(null,
					operation.getType(), operation.getAmount(), operation
							.getDate(), operation.getComment(), operation
							.getCategory());
			DepositWithdrawWindow window = new DepositWithdrawWindow(MainWindow
					.getInstance(), operation);
			window.setVisible(true);
			if (window.getResult() == ResultType.OK) {
				AccountOperation updatedOperation = window
						.getAccountOperation();
				if (App.execute(new EditAccountOperationAction(clonedOperation,
						updatedOperation)))
					GuiUtils.info("AccountOperationWindow.Operate.Success");
			}
		}
	}

	private List<AccountOperation> getSelectedOperations() {
		return MainWindow.getInstance().getContentPanel().getOperationsTable()
				.getSelectedAccountOperations();
	}
}
