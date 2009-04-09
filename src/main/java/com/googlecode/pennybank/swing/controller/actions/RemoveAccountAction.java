/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Action class which encapsulates the deletion of an account
 * 
 * @author spenap
 */
public class RemoveAccountAction extends GenericAction {

	private Account theAccount = null;
	private List<AccountOperation> deletedOperations = null;

	/**
	 * Creates the action with the specified arguments
	 * 
	 * @param theAccount
	 *            the account to remove
	 */
	public RemoveAccountAction(Account theAccount) {
		this.theAccount = theAccount;
		this.deletedOperations = new ArrayList<AccountOperation>();
	}

	protected boolean doExecute() {
		boolean success = false;
		deletedOperations.addAll(CommonOperations.saveAccountState(theAccount));
		CommonOperations.removeAccount(theAccount);
		return success;
	}

	protected void doUndo() {
		// Restore the account
		theAccount.setAccountId(null);
		theAccount = CommonOperations.createAccount(theAccount);

		// Restore the account operations
		for (AccountOperation deletedOperation : deletedOperations) {
			CommonOperations.createAccountOperation(deletedOperation);
		}
	}

	protected void doRedo() {
		deletedOperations.clear();
		execute();
	}

	protected String doGetName() {
		return MessageManager.getMessage("RemoveAccount");
	}

}
