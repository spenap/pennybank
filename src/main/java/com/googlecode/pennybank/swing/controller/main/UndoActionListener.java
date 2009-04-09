/**
 * 
 */
package com.googlecode.pennybank.swing.controller.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;

/**
 * @author spenap
 * 
 */
public class UndoActionListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		App.undoAction();
	}

}
