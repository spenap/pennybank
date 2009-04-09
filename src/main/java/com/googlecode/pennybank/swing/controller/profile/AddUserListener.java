/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.profile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.swing.controller.actions.AddUserAction;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.profile.ProfileWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;

/**
 * Listener which allows an user to get a window to create a new profile
 * 
 * @author spenap
 */
public class AddUserListener implements ActionListener {

	/**
	 * Method executed when the action is performed
	 * 
	 * @param e
	 *            The action event
	 */
	public void actionPerformed(ActionEvent e) {

        ProfileWindow window =
                new ProfileWindow(MainWindow.getInstance());
        window.setVisible(true);
        User theUser = window.getUser();
        if(App.execute(new AddUserAction(theUser)))
        	GuiUtils.info("UserWindow.CreateUser.Success");
    }
}
