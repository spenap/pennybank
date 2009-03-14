/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
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
			GuiUtils.info("AccountWindow.AccountNotSelected");
		} else {
			if (GuiUtils.confirm("AccountWindow.DeleteAccount") == ResultType.OK) {
				try {
					AccountFacadeDelegateFactory.getDelegate().deleteAccount(
							theAccount.getAccountId());
					MainWindow.getInstance().getNavigationPanel().update();
					GuiUtils.info("AccountWindow.DeleteAccount.Success");
				} catch (InstanceNotFoundException e1) {
					GuiUtils
							.error("AccountWindow.DeleteAccount.Failure.NotFound");
				} catch (InternalErrorException e1) {
					GuiUtils
							.error("AccountWindow.DeleteAccount.Failure.Generic");
				}
			}
		}
	}
}
