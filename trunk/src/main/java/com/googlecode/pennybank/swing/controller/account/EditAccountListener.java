/**
 * 
 */
package com.googlecode.pennybank.swing.controller.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.view.account.AccountWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;

/**
 * @author spenap
 * 
 */
public class EditAccountListener implements ActionListener {

	private Account theAccount = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		theAccount = MainWindow.getInstance().getNavigationPanel()
				.getSelectedAccount();
		if (theAccount == null) {
			GuiUtils.info("AccountWindow.AccountNotSelected");
		} else {
			AccountWindow dialog = new AccountWindow(MainWindow.getInstance(),
					theAccount.getUser(), theAccount);
			dialog.setVisible(true);
		}
	}

}
