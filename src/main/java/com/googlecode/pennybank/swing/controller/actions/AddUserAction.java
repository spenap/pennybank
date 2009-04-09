/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Action class which encapsulates the addition of an user.
 * 
 * @author spenap
 */
public class AddUserAction extends GenericAction {

	private User theUser = null;
	private List<Account> theAccounts = null;

	/**
	 * Creates the action with the user as a parameter
	 * 
	 * @param theUser The user to be added
	 */
	public AddUserAction(User theUser) {
		this.theUser = theUser;
		this.theAccounts = new ArrayList<Account>();
	}

	protected boolean doExecute() {
		theUser = CommonOperations.createUser(theUser);
		return theUser != null;
	}

	protected String doGetName() {
		return MessageManager.getMessage("CreateUser");
	}

	protected void doRedo() {
		theUser.setUserId(null);
		theUser.setAccounts(new ArrayList<Account>());
		doExecute();
	}

	protected void doUndo() {
		theAccounts.addAll(theUser.getAccounts());
		CommonOperations.removeUser(theUser);
	}

}
