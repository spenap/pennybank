/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.controller.actions.RemoveAccountAction;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

/**
 * 
 * @author spenap
 */
public class RemoveAccountListener implements ActionListener {

	private Account theAccount = null;

	public RemoveAccountListener() {
	}

	public void actionPerformed(ActionEvent e) {
		theAccount = MainWindow.getInstance().getNavigationPanel()
				.getSelectedAccount();
		if (theAccount == null) {
			GuiUtils.info("AccountWindow.AccountNotSelected");
		} else {
			if (GuiUtils.confirm("AccountWindow.DeleteAccount") == ResultType.OK) {
				if (App.execute(new RemoveAccountAction(theAccount)))
					GuiUtils.info("AccountWindow.DeleteAccount.Success");
			}
		}
	}
}
