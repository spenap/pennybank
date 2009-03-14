/**
 * 
 */
package com.googlecode.pennybank.swing.controller.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.view.category.AddCategoryWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class AddCategoryListener implements ActionListener, ItemListener {

	private JComboBox comboBox = null;

	public AddCategoryListener() {

	}

	public AddCategoryListener(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		createCategory();
	}

	private Category createCategory() {
		AddCategoryWindow window = new AddCategoryWindow(MainWindow
				.getInstance());
		window.setVisible(true);
		Category createdCategory = window.getCreatedCategory();
		if (createdCategory != null)
			App.getCategories().add(createdCategory);
		return window.getCreatedCategory();
	}

	public void itemStateChanged(ItemEvent e) {
		if ((e.getStateChange() == ItemEvent.SELECTED)
				&& (e.getItem().toString().equals(MessageManager
						.getMessage("Category.New")))) {
			Category createdCategory = createCategory();
			populateComboBox(createdCategory);
		}
	}

	private void populateComboBox(Category createdCategory) {
		comboBox.removeAllItems();
		comboBox.addItem(MessageManager.getMessage("Category.Uncategorized"));
		for (Category category : App.getCategories()) {
			comboBox.addItem(category.getName());
		}
		comboBox.addItem(MessageManager.getMessage("Category.New"));
		if (createdCategory != null)
			comboBox.setSelectedItem(createdCategory.getName());
	}

}
