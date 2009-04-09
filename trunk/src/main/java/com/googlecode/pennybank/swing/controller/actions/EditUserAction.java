/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Action class which encapsulates the edition of an user, allowing undoing and
 * redoing the changes.
 * 
 * @author spenap
 */
public class EditUserAction extends GenericAction {

	private User currentUser = null;
	private User previousUser = null;

	/**
	 * Creates the action with the specified arguments
	 * 
	 * @param previousUser
	 *            The user before the update
	 * @param updatedUser
	 *            The user after the update
	 */
	public EditUserAction(User previousUser, User updatedUser) {
		this.previousUser = previousUser;
		this.currentUser = updatedUser;
	}

	protected boolean doExecute() {
		boolean success = false;
		CommonOperations.updateUser(currentUser);
		return success;
	}

	protected String doGetName() {
		return MessageManager.getMessage("EditUser");
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
		String tmpName = currentUser.getName();
		currentUser.setName(previousUser.getName());
		previousUser.setName(tmpName);
	}

}
