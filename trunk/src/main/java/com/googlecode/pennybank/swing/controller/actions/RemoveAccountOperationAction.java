package com.googlecode.pennybank.swing.controller.actions;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Class encapsulating the information needed to allow undoing and redoing the
 * deletion of an account operation
 * 
 * @author spenap
 */
public class RemoveAccountOperationAction extends GenericAction {

	private AccountOperation theAccountOperation = null;

	/**
	 * Creates the action with the specified arguments
	 * 
	 * @param theAccountOperation
	 *            the account operation to be removed
	 */
	public RemoveAccountOperationAction(AccountOperation theAccountOperation) {
		this.theAccountOperation = theAccountOperation;
	}

	@Override
	protected boolean doExecute() {
		CommonOperations.removeAccountOperation(theAccountOperation);
		return true;
	}

	@Override
	protected String doGetName() {
		return MessageManager.getMessage("RemoveAccountOperation."
				+ theAccountOperation.getType());
	}

	@Override
	protected void doRedo() {
		doExecute();
	}

	@Override
	protected void doUndo() {
		theAccountOperation = CommonOperations
				.createAccountOperation(theAccountOperation);
	}

}
