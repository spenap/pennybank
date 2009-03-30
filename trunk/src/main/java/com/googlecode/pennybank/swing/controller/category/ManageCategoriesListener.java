/**
 * 
 */
package com.googlecode.pennybank.swing.controller.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.swing.view.category.ManageCategoriesWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;

/**
 * @author spenap
 * 
 */
public class ManageCategoriesListener implements ActionListener {

	private ManageCategoriesWindow window = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		getManageCategoriesWindow().setVisible(true);
	}

	private ManageCategoriesWindow getManageCategoriesWindow() {
		if (window == null) {
			window = new ManageCategoriesWindow(MainWindow.getInstance());
		}
		return window;
	}

}
