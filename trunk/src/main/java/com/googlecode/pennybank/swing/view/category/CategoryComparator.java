/**
 * 
 */
package com.googlecode.pennybank.swing.view.category;

import java.util.Comparator;

import com.googlecode.pennybank.model.category.entity.Category;

/**
 * @author spenap
 * 
 */
public class CategoryComparator implements Comparator<Category> {

	public int compare(Category aCategory, Category anotherCategory) {

		if (aCategory.equals(anotherCategory)) {
			return 0;
		} else if (aCategory.getName().equals(anotherCategory.getName())) {
			if (aCategory.getParentCategory() == null
					&& anotherCategory.getParentCategory() != null) {
				return -1;
			} else if (aCategory.getParentCategory() == null
					&& anotherCategory.getParentCategory() == null) {
				return 0;
			} else
				return compare(aCategory.getParentCategory(), anotherCategory
						.getParentCategory());
		} else
			return aCategory.getName().compareTo(anotherCategory.getName());
	}

}
