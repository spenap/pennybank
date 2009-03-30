/**
 * 
 */
package com.googlecode.pennybank.swing.controller.profile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.profile.ProfileWindow;

/**
 * @author spenap
 * 
 */
public class EditUserListener implements ActionListener {

	private User theUser = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		theUser = MainWindow.getInstance().getNavigationPanel()
				.getSelectedUser();

		ProfileWindow window = new ProfileWindow(MainWindow.getInstance(),
				theUser);
		window.setVisible(true);
	}

}
