/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Action class which encapsulates the addition of an account
 * 
 * @author spenap
 */
public class AddAccountAction extends GenericAction {

	private Account theAccount = null;

	/**
	 * Creates the action with the account as a parameter
	 * 
	 * @param theAccount The account to be added
	 */
	public AddAccountAction(Account theAccount) {
		this.theAccount = theAccount;
	}

	protected boolean doExecute() {
		theAccount = CommonOperations.createAccount(theAccount);
		return theAccount != null;
	}

	protected void doUndo() {
		CommonOperations.removeAccount(theAccount);
	}

	protected void doRedo() {
		theAccount.setAccountId(null);
		doExecute();
	}

	protected String doGetName() {
		return MessageManager.getMessage("CreateAccount");
	}

}
