/**
 * NavigationPanelListener.java
 * 
 * 06/03/2009
 */
package com.googlecode.pennybank.swing.controller.navigationpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.swing.controller.account.AddAccountListener;
import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.controller.profile.AddUserListener;
import com.googlecode.pennybank.swing.controller.profile.RemoveUserListener;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class NavigationPanelListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());

		if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.AddUser"))) {
			new AddUserListener().actionPerformed(e);
		} else if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.AddAccount"))) {
			new AddAccountListener().actionPerformed(e);
		} else if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.RemoveUser"))) {
			new RemoveUserListener().actionPerformed(e);
		} else if (e.getActionCommand().equals(
				MessageManager.getMessage("NavigationPanel.RemoveAccount"))) {
			new RemoveAccountListener().actionPerformed(e);
		}
	}
}
