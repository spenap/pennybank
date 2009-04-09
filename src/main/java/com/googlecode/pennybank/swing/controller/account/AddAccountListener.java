/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.controller.actions.AddAccountAction;
import com.googlecode.pennybank.swing.view.account.AccountWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

/**
 * Listener which creates a new window to add an account
 * 
 * @author spenap
 */
public class AddAccountListener implements ActionListener {

	private User theUser;

	/**
	 * Method executed when the action is performed
	 * 
	 * @param e
	 *            The action event
	 */
	public void actionPerformed(ActionEvent e) {
		theUser = MainWindow.getInstance().getNavigationPanel()
				.getSelectedUser();

		if (theUser != null) {
			AccountWindow dialog = new AccountWindow(MainWindow.getInstance(),
					theUser);
			dialog.setVisible(true);
			if (dialog.getResult() == ResultType.OK) {
				Account theAccount = dialog.getAccount();
				if (App.execute(new AddAccountAction(theAccount)))
					GuiUtils.info("AccountWindow.CreateAccount.Success");
			}
		} else {
			GuiUtils.warn("AccountWindow.UserNotSelected");
		}

	}
}
