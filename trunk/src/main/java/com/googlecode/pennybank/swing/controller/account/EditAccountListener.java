/**
 * 
 */
package com.googlecode.pennybank.swing.controller.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.controller.actions.EditAccountAction;
import com.googlecode.pennybank.swing.view.account.AccountWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

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
		Account clonedAccount = null;
		theAccount = MainWindow.getInstance().getNavigationPanel()
				.getSelectedAccount();

		if (theAccount != null) {
			clonedAccount = new Account();
			clonedAccount.setBalance(theAccount.getBalance());
			clonedAccount.setName(theAccount.getName());
			AccountWindow dialog = new AccountWindow(MainWindow.getInstance(),
					theAccount);
			dialog.setVisible(true);
			if (dialog.getResult() == ResultType.OK) {
				Account theAccount = dialog.getAccount();
				if (App
						.execute(new EditAccountAction(clonedAccount,
								theAccount)))
					GuiUtils.info("AccountWindow.UpdateAccount.Success");
			}
		} else {
			GuiUtils.info("AccountWindow.AccountNotSelected");
		}
	}

}
