/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Action class which encapsulates the information needed for undoing and
 * redoing the addition of an account operation
 * 
 * @author spenap
 */
public class AddAccountOperationAction extends GenericAction {

	private AccountOperation theAccountOperation = null;

	/**
	 * Creates the action with the specified arguments
	 * 
	 * @param theAccountOperation
	 *            the AccountOperation to be created
	 */
	public AddAccountOperationAction(AccountOperation theAccountOperation) {
		this.theAccountOperation = theAccountOperation;
	}

	@Override
	protected boolean doExecute() {
		theAccountOperation = CommonOperations
				.createAccountOperation(theAccountOperation);
		return true;
	}

	@Override
	protected String doGetName() {
		return MessageManager.getMessage("AddAccountOperation."
				+ theAccountOperation.getType());
	}

	@Override
	protected void doRedo() {
		doExecute();
	}

	@Override
	protected void doUndo() {
		CommonOperations.removeAccountOperation(theAccountOperation);
	}

}
