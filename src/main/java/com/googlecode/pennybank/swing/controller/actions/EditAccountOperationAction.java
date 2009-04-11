package com.googlecode.pennybank.swing.controller.actions;

import java.util.Calendar;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.view.util.MessageManager;

public class EditAccountOperationAction extends GenericAction {

	private AccountOperation previousOperation = null;
	private AccountOperation currentOperation = null;

	public EditAccountOperationAction(AccountOperation previousOperation,
			AccountOperation currentOperation) {
		this.previousOperation = previousOperation;
		this.currentOperation = currentOperation;
	}

	@Override
	protected boolean doExecute() {
		CommonOperations.updateAccountOperation(currentOperation);
		return true;
	}

	@Override
	protected String doGetName() {
		return MessageManager.getMessage("EditAccountOperation");
	}

	@Override
	protected void doRedo() {
		swapOperationValues();
		doExecute();
	}

	@Override
	protected void doUndo() {
		swapOperationValues();
		doExecute();
	}

	private void swapOperationValues() {
		double amount = currentOperation.getAmount();
		String comment = currentOperation.getComment();
		Calendar date = currentOperation.getDate();
		Category category = currentOperation.getCategory();

		currentOperation.setAmount(previousOperation.getAmount());
		currentOperation.setComment(previousOperation.getComment());
		currentOperation.setDate(previousOperation.getDate());
		currentOperation.setCategory(previousOperation.getCategory());

		previousOperation.setAmount(amount);
		previousOperation.setComment(comment);
		previousOperation.setDate(date);
		previousOperation.setCategory(category);
	}
}
