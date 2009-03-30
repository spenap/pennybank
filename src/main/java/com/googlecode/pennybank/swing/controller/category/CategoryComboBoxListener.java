/**
 * 
 */
package com.googlecode.pennybank.swing.controller.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.view.category.CategoriesComboBox;
import com.googlecode.pennybank.swing.view.category.ShowCategoryWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class CategoryComboBoxListener implements ActionListener {

	private CategoriesComboBox comboBox = null;

	public CategoryComboBoxListener(CategoriesComboBox comboBox) {
		this.comboBox = comboBox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (comboBox.getSelectedItem().toString().equals(
				MessageManager.getMessage("Category.New"))) {
			Category createdCategory = createCategory();
			populateComboBox(createdCategory);
		}
	}

	private Category createCategory() {
		ShowCategoryWindow window = new ShowCategoryWindow(MainWindow
				.getInstance());
		window.setVisible(true);
		Category createdCategory = window.getCreatedCategory();
		if (createdCategory != null)
			App.getCategories().add(createdCategory);
		return window.getCreatedCategory();
	}

	private void populateComboBox(Category createdCategory) {
		comboBox.updateModel();
		if (createdCategory != null)
			comboBox.setSelectedItem(createdCategory.getName());
	}

}
