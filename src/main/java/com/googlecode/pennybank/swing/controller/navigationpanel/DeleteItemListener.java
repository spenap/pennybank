/**
 * 
 */
package com.googlecode.pennybank.swing.controller.navigationpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.controller.profile.RemoveUserListener;
import com.googlecode.pennybank.swing.view.main.MainNavigationPanel;
import com.googlecode.pennybank.swing.view.main.MainWindow;

/**
 * @author spenap
 * 
 */
public class DeleteItemListener implements ActionListener {

	private RemoveUserListener userListener = null;
	private RemoveAccountListener accountListener = null;

	public DeleteItemListener() {
		userListener = new RemoveUserListener();
		accountListener = new RemoveAccountListener();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		MainNavigationPanel navigationPanel = MainWindow.getInstance()
				.getNavigationPanel();

		if (navigationPanel.getSelectedAccount() != null) {
			accountListener.actionPerformed(e);
		} else if (navigationPanel.getSelectedUser() != null) {
			userListener.actionPerformed(e);
		}
	}

}
