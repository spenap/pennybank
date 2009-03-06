/**
 * RemoveUserListener.java
 * 
 * 06/03/2009
 */
package com.googlecode.pennybank.swing.controller.profile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.MessageBox;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.MessageBox.MessageType;
import com.googlecode.pennybank.swing.view.util.MessageBox.ResultType;

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
			MessageBox messageBox = new MessageBox(
					MainWindow.getInstance(),
					MessageManager
							.getMessage("UserWindow.UserNotSelected.Title"),
					MessageManager
							.getMessage("UserWindow.UserNotSelected.Description"),
					MessageType.INFORMATION);
			messageBox.setVisible(true);
		} else {
			MessageBox messageBox = new MessageBox(
					MainWindow.getInstance(),
					MessageManager
							.getMessage("UserWindow.DeleteUser.Title"),
					MessageManager
							.getMessage("UserWindow.DeleteUser.Description"),
					MessageType.YESNO);
			messageBox.setVisible(true);
			if (messageBox.getWindowResult() == ResultType.OK) {

			}
		}
	}

}
