/**
 * 
 */
package com.googlecode.pennybank.swing.controller.accountoperation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

/**
 * @author spenap
 * 
 */
public class DeleteAccountOperationListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		List<AccountOperation> operations = getSelectedOperations();

		if (operations.size() > 0
				&& GuiUtils.confirm("AccountOperationWindow.Delete") == ResultType.OK) {

			try {
				for (AccountOperation operation : operations) {
					AccountFacadeDelegateFactory.getDelegate()
							.deleteAccountOperation(
									operation.getOperationIdentifier());
				}
				MainWindow.getInstance().getContentPanel()
						.showAccountOperations(operations.get(0).getAccount());
				GuiUtils.info("AccountOperationWindow.Delete.Success");
			} catch (InstanceNotFoundException e1) {
				GuiUtils
						.error("AccountOperationWindow.Delete.Failure.NotFound");
			} catch (InternalErrorException e1) {
				GuiUtils.error("AccountOperationWindow.Delete.Failure.Generic");
			}
		}
	}

	private List<AccountOperation> getSelectedOperations() {
		return MainWindow.getInstance().getContentPanel().getOperationsTable()
				.getSelectedAccountOperations();
	}

}
