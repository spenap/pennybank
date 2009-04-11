/**
 * 
 */
package com.googlecode.pennybank.swing.controller.accountoperation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.controller.actions.CompositeAction;
import com.googlecode.pennybank.swing.controller.actions.GenericAction;
import com.googlecode.pennybank.swing.controller.actions.RemoveAccountOperationAction;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

/**
 * ActionListener which allows the deletion of a given number of account
 * operations.
 * 
 * @author spenap
 */
public class RemoveAccountOperationListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		List<AccountOperation> operations = getSelectedOperations();

		if (operations.size() > 0
				&& GuiUtils.confirm("AccountOperationWindow.Delete") == ResultType.OK) {

			List<GenericAction> actions = new ArrayList<GenericAction>();
			for (AccountOperation operation : operations) {
				actions.add(new RemoveAccountOperationAction(operation));
			}
			if (App.execute(new CompositeAction(actions, MessageManager
					.getMessage("AccountOperation.Delete")
					+ " " + operations.size())))
				GuiUtils.info("AccountOperationWindow.Delete.Success");
		}
	}

	private List<AccountOperation> getSelectedOperations() {
		return MainWindow.getInstance().getContentPanel().getOperationsTable()
				.getSelectedAccountOperations();
	}

}
