/**
 * AddAccountOperationListener.java
 * 
 * 02/03/2009
 */
package com.googlecode.pennybank.swing.controller.accountoperation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.swing.view.accountoperation.DepositWithdrawWindow;
import com.googlecode.pennybank.swing.view.accountoperation.TransferWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.MessageBox;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.MessageBox.MessageType;

/**
 * Listener who allows to operate an account
 * 
 * @author spenap
 */
public class AddAccountOperationListener implements ActionListener {

	public enum OperationType {
		DEPOSIT, WITHDRAW, TRANSFER
	};

	private OperationType type;
	private Account operatedAccount;

	/**
	 * Creates a listener with the specified arguments
	 * 
	 * @param type
	 */
	public AddAccountOperationListener(OperationType type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		operatedAccount = MainWindow.getInstance().getNavigationPanel()
				.getSelectedAccount();

		if (operatedAccount == null) {
			MessageBox messageBox = new MessageBox(
					MainWindow.getInstance(),
					MessageManager
							.getMessage("AccountOperationWindow.AccountNotSelected.Title"),
					MessageManager
							.getMessage("AccountOperationWindow.AccountNotSelected.Description"),
					MessageType.INFORMATION);
			messageBox.setVisible(true);
		} else {
			if (type != OperationType.TRANSFER) {
				DepositWithdrawWindow dialog = null;
				switch (type) {
				case DEPOSIT:
					dialog = new DepositWithdrawWindow(
							MainWindow.getInstance(), Type.DEPOSIT,
							operatedAccount);

					break;
				case WITHDRAW:
					dialog = new DepositWithdrawWindow(
							MainWindow.getInstance(), Type.WITHDRAW,
							operatedAccount);
					break;
				default:
					break;
				}
				dialog.setVisible(true);
			} else {
				new TransferWindow(MainWindow.getInstance(), operatedAccount)
						.setVisible(true);
			}
		}
	}

}
