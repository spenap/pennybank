/**
 * RemoveUserListener.java
 * 
 * 06/03/2009
 */
package com.googlecode.pennybank.swing.controller.profile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.controller.actions.RemoveUserAction;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.ResultWindow.ResultType;

/**
 * @author spenap
 * 
 */
public class RemoveUserListener implements ActionListener {

	private User toDelete;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		toDelete = MainWindow.getInstance().getNavigationPanel()
				.getSelectedUser();
		if (toDelete == null) {
			GuiUtils.info("UserWindow.UserNotSelected");
		} else {
			if (GuiUtils.confirm("UserWindow.DeleteUser") == ResultType.OK) {

				if (App.execute(new RemoveUserAction(toDelete)))
					GuiUtils.info("UserWindow.DeleteUser.Success");
			}
		}
	}
}
