/**
 * 
 */
package com.googlecode.pennybank.swing.view.category;

import javax.swing.JComboBox;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.controller.category.CategoryComboBoxListener;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Combo box extension which allow an user to pick up between the existing
 * categories, choose a non-categorized option, or create a new one
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class CategoriesComboBox extends JComboBox {

	/**
	 * Creates a combo box populated with the existing categories, following the
	 * conventions of a pop up menu.
	 */
	public CategoriesComboBox() {
		this.putClientProperty("JComboBox.isPopDown", Boolean.FALSE);
		updateModel();
		addActionListener(new CategoryComboBoxListener(this));
	}

	/**
	 * Updates the combo box contents
	 */
	public void updateModel() {
		this.removeAllItems();
		this.addItem(MessageManager.getMessage("Category.Uncategorized"));
		for (Category category : App.getCategories()) {
			this.addItem(category.getName());
		}
		this.addItem(MessageManager.getMessage("Category.New"));
	}
}
