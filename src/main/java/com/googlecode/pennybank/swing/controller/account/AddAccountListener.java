/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.view.account.AddAccountWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;

/**
 * Listener which creates a new window to add an account
 * 
 * @author spenap
 */
public class AddAccountListener implements ActionListener {

	private User theUser;

	/**
	 * Method executed when the action is performed
	 * 
	 * @param e
	 *            The action event
	 */
	public void actionPerformed(ActionEvent e) {
		theUser = MainWindow.getInstance().getNavigationPanel()
				.getSelectedUser();

		if (theUser != null) {
			AddAccountWindow dialog = new AddAccountWindow(MainWindow
					.getInstance(), theUser);
			dialog.setVisible(true);
		} else {
			GuiUtils.warn("AccountWindow.UserNotSelected");
		}

	}
}
