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
import com.googlecode.pennybank.swing.view.accountoperation.AddAccountOperationWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;

/**
 * Listener who allows to operate an account
 * 
 * @author spenap
 */
public class AddAccountOperationListener implements ActionListener {

	private Type type;

	/**
	 * Creates a listener with the specified arguments
	 * 
	 * @param type
	 */
	public AddAccountOperationListener(Type type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Account operatedAccount = MainWindow.getInstance().getNavigationPanel()
				.getSelectedAccount();
		AddAccountOperationWindow dialog = new AddAccountOperationWindow(
				MainWindow.getInstance(), true, type, operatedAccount);
		dialog.setVisible(true);
	}

}
