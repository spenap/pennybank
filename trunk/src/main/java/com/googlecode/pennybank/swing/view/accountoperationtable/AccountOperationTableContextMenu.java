/**
 * 
 */
package com.googlecode.pennybank.swing.view.accountoperationtable;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import com.googlecode.pennybank.swing.controller.accountoperation.DeleteAccountOperationListener;
import com.googlecode.pennybank.swing.controller.accountoperation.EditAccountOperationListener;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
@SuppressWarnings("serial")
public class AccountOperationTableContextMenu extends JPopupMenu {

	private JMenuItem deleteOperationItem = null;
	private JMenuItem updateOperationItem = null;

	public AccountOperationTableContextMenu(JTable theTable) {

		super();
		boolean singleItemSelected = false;
		singleItemSelected = theTable.getSelectedRows().length == 1;
		getUpdateOperationItem().setEnabled(singleItemSelected);
		this.add(getUpdateOperationItem());
		this.add(getDeleteOperationItem());
	}

	private JMenuItem getDeleteOperationItem() {
		if (deleteOperationItem == null) {
			deleteOperationItem = new JMenuItem(MessageManager
					.getMessage("AccountOperationTable.ContextMenu.Delete"));
			deleteOperationItem
					.addActionListener(new DeleteAccountOperationListener());
		}
		return deleteOperationItem;
	}

	private JMenuItem getUpdateOperationItem() {
		if (updateOperationItem == null) {
			updateOperationItem = new JMenuItem(MessageManager
					.getMessage("AccountOperationTable.ContextMenu.Edit"));
			updateOperationItem
					.addActionListener(new EditAccountOperationListener());
		}
		return updateOperationItem;
	}

}
