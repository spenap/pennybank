/**
 * 
 */
package com.googlecode.pennybank.swing.controller.profile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.controller.actions.EditUserAction;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.profile.ProfileWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

/**
 * @author spenap
 * 
 */
public class EditUserListener implements ActionListener {

	private User updatedUser = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		User clonedUser = null;

		updatedUser = MainWindow.getInstance().getNavigationPanel()
				.getSelectedUser();

		clonedUser = new User(updatedUser.getName());

		ProfileWindow window = new ProfileWindow(MainWindow.getInstance(),
				updatedUser);
		window.setVisible(true);
		if (window.getResult() == ResultType.OK) {
			updatedUser = window.getUser();
			if (App.execute(new EditUserAction(clonedUser,updatedUser)))
				GuiUtils.info("UserWindow.UpdateUser.Success");
		}
	}

}
