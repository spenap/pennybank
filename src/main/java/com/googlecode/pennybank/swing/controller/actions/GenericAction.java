/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.view.main.MainContentPanel;
import com.googlecode.pennybank.swing.view.main.MainNavigationPanel;
import com.googlecode.pennybank.swing.view.main.MainWindow;

/**
 * Generic action implementation providing common behavior for all the actions.
 * A template pattern is employed, so each action must override specific
 * doExecute, doRedo, doUndo and doGetName methods. The execute, redo, undo and
 * getName methods are marked as final, so the extending classes doesn't
 * override them.
 * 
 * @author spenap
 * 
 */
public abstract class GenericAction implements UIAction {

	public final boolean execute() {
		boolean value = doExecute();
		update();
		return value;
	}

	public final String getName() {
		return doGetName();
	}

	public final void redo() {
		doRedo();
		update();
	}

	public final void undo() {
		doUndo();
		update();
	}

	private void update() {
		Account selectedAccount = null;
		MainNavigationPanel navigationPanel = null;
		MainContentPanel contentPanel = null;

		navigationPanel = MainWindow.getInstance().getNavigationPanel();
		contentPanel = MainWindow.getInstance().getContentPanel();
		selectedAccount = navigationPanel.getSelectedAccount();

		navigationPanel.update();
		if (selectedAccount != null)
			contentPanel.showAccountOperations(selectedAccount);
	}

	protected abstract boolean doExecute();

	protected abstract String doGetName();

	protected abstract void doRedo();

	protected abstract void doUndo();

}
