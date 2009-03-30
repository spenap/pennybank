/**
 * 
 */
package com.googlecode.pennybank.swing.controller.navigationpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.swing.controller.account.EditAccountListener;
import com.googlecode.pennybank.swing.controller.profile.EditUserListener;
import com.googlecode.pennybank.swing.view.main.MainNavigationPanel;
import com.googlecode.pennybank.swing.view.main.MainWindow;

/**
 * @author spenap
 * 
 */
public class EditItemListener implements ActionListener {

	private EditAccountListener accountListener = null;
	private EditUserListener userListener = null;

	public EditItemListener() {
		accountListener = new EditAccountListener();
		userListener = new EditUserListener();
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
