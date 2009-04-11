/**
 * AddAccountOperationListener.java
 * 
 * 02/03/2009
 */
package com.googlecode.pennybank.swing.controller.accountoperation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.swing.controller.actions.AddAccountOperationAction;
import com.googlecode.pennybank.swing.view.accountoperation.DepositWithdrawWindow;
import com.googlecode.pennybank.swing.view.accountoperation.TransferWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

/**
 * ActionListener which invokes the window needed to generate the account
 * operation desired ( add to an account, withdraw from an account or transfer
 * between accounts), and later creates the action which allows undoing and
 * redoing that account operation
 * 
 * @author spenap
 */
public class AddAccountOperationListener implements ActionListener {

	/**
	 * Enumerate defining the three different account operations
	 * 
	 * @author spenap
	 */
	public enum OperationType {
		DEPOSIT, WITHDRAW, TRANSFER
	};

	private OperationType type;
	private Account operatedAccount;

	/**
	 * Creates the listener for the specific operation
	 * 
	 * @param type The operation type
	 */
	public AddAccountOperationListener(OperationType type) {
		this.type = type;
	}

	public void actionPerformed(ActionEvent e) {

		operatedAccount = MainWindow.getInstance().getNavigationPanel()
				.getSelectedAccount();

		if (operatedAccount == null) {
			GuiUtils.info("AccountOperationWindow.AccountNotSelected");
		} else {
			if (type == OperationType.TRANSFER) {
				new TransferWindow(MainWindow.getInstance(), operatedAccount)
						.setVisible(true);
			} else {
				Type operationType = null;
				if (type == OperationType.DEPOSIT) {
					operationType = Type.DEPOSIT;
				} else {
					operationType = Type.WITHDRAW;
				}
				DepositWithdrawWindow dialog = new DepositWithdrawWindow(
						MainWindow.getInstance(), operationType,
						operatedAccount);
				dialog.setVisible(true);
				if (dialog.getResult() == ResultType.OK) {
					AccountOperation theAccountOperation = dialog
							.getAccountOperation();
					App.execute(new AddAccountOperationAction(
							theAccountOperation));
				}
			}
		}
	}
}
