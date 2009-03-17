/**
 * 
 */
package com.googlecode.pennybank.swing.controller.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;

/**
 * @author spenap
 * 
 */
public class TableSearchActionListener implements ActionListener {

	List<JCheckBoxMenuItem> menuItems = new ArrayList<JCheckBoxMenuItem>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JCheckBoxMenuItem) {
			updateItemsState((JCheckBoxMenuItem) e.getSource());
		}
	}

	private void updateItemsState(JCheckBoxMenuItem selectedItem) {
		for (JCheckBoxMenuItem menuItem : menuItems) {
			menuItem.setSelected(menuItem.equals(selectedItem));
		}
	}

	public void add(JCheckBoxMenuItem menuItem) {
		menuItems.add(menuItem);
	}

}
