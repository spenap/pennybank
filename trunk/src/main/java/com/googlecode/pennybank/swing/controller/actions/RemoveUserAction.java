/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Action class which encapsulates the deletion of an user.
 * 
 * @author spenap
 */
public class RemoveUserAction extends GenericAction {

	private User theUser = null;
	private Map<Account, List<AccountOperation>> deletedOperations = null;

	/**
	 * Creates an action with the specified arguments
	 * 
	 * @param theUser
	 *            to be created
	 */
	public RemoveUserAction(User theUser) {
		this.theUser = theUser;
		deletedOperations = new HashMap<Account, List<AccountOperation>>();
	}

	protected boolean doExecute() {
		boolean success = false;
		for (Account accountToDelete : theUser.getAccounts()) {
			deletedOperations.put(accountToDelete, CommonOperations
					.saveAccountState(accountToDelete));
		}
		CommonOperations.removeUser(theUser);
		return success;
	}

	protected String doGetName() {
		return MessageManager.getMessage("RemoveUser");
	}

	protected void doRedo() {
		deletedOperations.clear();
		doExecute();
	}

	protected void doUndo() {
		// Restore the user
		theUser.setUserId(null);
		theUser.setAccounts(new ArrayList<Account>());
		theUser = CommonOperations.createUser(theUser);

		for (Account deletedAccount : deletedOperations.keySet()) {
			// Restore the account
			deletedAccount.setAccountId(null);
			CommonOperations.createAccount(deletedAccount);

			for (AccountOperation deletedOperation : deletedOperations
					.get(deletedAccount)) {
				// Restore the account operations
				CommonOperations.createAccountOperation(deletedOperation);
			}
		}
	}

}
