/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.profile;

import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.profile.AddProfileWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener which allows an user to get a window to create a new profile
 * 
 * @author spenap
 */
public class AddProfileListener implements ActionListener {

    /**
     * Method executed when the action is performed
     *
     * @param e The action event
     */
    public void actionPerformed(ActionEvent e) {

        AddProfileWindow window =
                new AddProfileWindow(MainWindow.getInstance(), true);
        window.setVisible(true);

    }
}
