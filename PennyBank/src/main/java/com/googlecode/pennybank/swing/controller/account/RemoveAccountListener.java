/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.MessageBox;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.MessageBox.MessageType;
import com.googlecode.pennybank.swing.view.util.MessageBox.ResultType;

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
			MessageBox messageBox = new MessageBox(
					MainWindow.getInstance(),
					MessageManager
							.getMessage("AccountWindow.AccountNotSelected.Title"),
					MessageManager
							.getMessage("AccountWindow.AccountNotSelected.Description"),
					MessageType.INFORMATION);
			messageBox.setVisible(true);
		} else {
			MessageBox messageBox = new MessageBox(
					MainWindow.getInstance(),
					MessageManager
							.getMessage("AccountWindow.DeleteAccount.Title"),
					MessageManager
							.getMessage("AccountWindow.DeleteAccount.Description"),
					MessageType.YESNO);
			messageBox.setVisible(true);
			if (messageBox.getWindowResult() == ResultType.OK) {

			}
		}
	}
}
