/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Action class which encapsulates the edition of an account, allowing undoing
 * and redoing the changes.
 * 
 * @author spenap
 */
public class EditAccountAction extends GenericAction {

	private Account updatedAccount = null;
	private Account previousAccount = null;

	/**
	 * Creates the action with the specified arguments.
	 * 
	 * @param previousAccount
	 *            The account before the modifications
	 * @param theAccount
	 *            The account after the modifications
	 */
	public EditAccountAction(Account previousAccount, Account theAccount) {
		this.previousAccount = previousAccount;
		this.updatedAccount = theAccount;
	}

	protected boolean doExecute() {
		CommonOperations.updateAccount(updatedAccount);
		return true;
	}

	protected String doGetName() {
		return MessageManager.getMessage("EditAccount");
	}

	protected void doRedo() {
		swapPreviousValues();
		doExecute();
	}

	protected void doUndo() {
		swapPreviousValues();
		doExecute();
	}

	private void swapPreviousValues() {
		String tmpName = updatedAccount.getName();
		double tmpBalance = updatedAccount.getBalance();
		updatedAccount.setBalance(previousAccount.getBalance());
		updatedAccount.setName(previousAccount.getName());
		previousAccount.setBalance(tmpBalance);
		previousAccount.setName(tmpName);
	}

}
