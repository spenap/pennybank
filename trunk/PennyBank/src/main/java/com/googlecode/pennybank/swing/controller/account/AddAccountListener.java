/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.account;

import com.googlecode.pennybank.swing.view.account.AddAccountWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener which creates a new window to add an account
 *
 * @author spenap
 */
public class AddAccountListener implements ActionListener {

    /**
     * Method executed when the action is performed
     *
     * @param e The action event
     */
    public void actionPerformed(ActionEvent e) {

        AddAccountWindow dialog =
                new AddAccountWindow(MainWindow.getInstance(), true);
        dialog.setVisible(true);
    }
}
