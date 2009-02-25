/**
 * CreateCategoryTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;

/**
 * @author spenap
 * 
 */
public class CreateCategoryTest {

	private static AccountFacadeDelegate accountFacade;
	private static final long NON_EXISTENT_CATEGORY_ID = -1;
	private static List<Category> usedCategories;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		accountFacade = AccountFacadeDelegateFactory.getDelegate();
		usedCategories = new ArrayList<Category>();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		for (Category category : usedCategories) {
			accountFacade.deleteCategory(category.getCategoryId());
		}
	}

	/**
	 * Method which tests the behavior of the account facade when creating a
	 * root category
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testCreateRootCategory() throws InstanceNotFoundException,
			InternalErrorException {

		Category rootCategory = new Category("Root Category");
		rootCategory = accountFacade.createCategory(rootCategory);
		Category theCategory = accountFacade.findCategory(rootCategory
				.getCategoryId());
		assertEquals(rootCategory, theCategory);
		usedCategories.add(rootCategory);
	}

	/**
	 * Method which tests the behavior of the account facade when creating a
	 * leaf category with an existing root
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testCreateLeafCategoryWithExistingRoot()
			throws InstanceNotFoundException, InternalErrorException {
		Category rootCategory = new Category("Root Category");
		rootCategory = accountFacade.createCategory(rootCategory);
		Category leafCategory = new Category("Leaf Category", rootCategory);
		leafCategory = accountFacade.createCategory(leafCategory);
		Category theCategory = accountFacade.findCategory(leafCategory
				.getCategoryId());
		assertEquals(theCategory, leafCategory);
		assertEquals(theCategory.getParentCategory(), rootCategory);
		usedCategories.add(rootCategory);
		usedCategories.add(leafCategory);
	}

	/**
	 * Method which tests the behavior of the account facade when creating a
	 * leaf category with a non-existent root
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void testCreateLeafCategoryWithoutExistingRoot()
			throws InstanceNotFoundException, InternalErrorException {
		Category rootCategory = new Category("Root Category");
		rootCategory.setCategoryId(NON_EXISTENT_CATEGORY_ID);
		Category leafCategory = new Category("Leaf Category", rootCategory);
		leafCategory = accountFacade.createCategory(leafCategory);
	}
}
