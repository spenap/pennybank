/**
 * DeleteCategoryTest.java
 * 
 * 24/02/2009
 */
package com.googlecode.pennybank.model.accountfacade.actions;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
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
public class DeleteCategoryTest {

	private static AccountFacadeDelegate accountFacade;
	private Category rootCategory;
	private static final long NON_EXISTENT_CATEGORY_ID = -1;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		accountFacade = AccountFacadeDelegateFactory.getDelegate();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rootCategory = new Category("Root Category");
		rootCategory = accountFacade.createCategory(rootCategory);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		accountFacade.deleteCategory(rootCategory.getCategoryId());
	}

	/**
	 * Method for testing the deletion of an existent category without a parent
	 * category
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testDeleteRootCategoryExistentCategory()
			throws InstanceNotFoundException, InternalErrorException {

		Category theCategory = new Category("Test Category");
		theCategory = accountFacade.createCategory(theCategory);
		accountFacade.deleteCategory(theCategory.getCategoryId());
		boolean exceptionCatched = false;
		try {
			accountFacade.findCategory(theCategory.getCategoryId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

	/**
	 * Method for testing the deletion of an existent category with a parent
	 * category
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testDeleteLeafCategoryExistentCategory()
			throws InstanceNotFoundException, InternalErrorException {

		Category theCategory = new Category("Test Category", rootCategory);
		theCategory = accountFacade.createCategory(theCategory);
		accountFacade.deleteCategory(theCategory.getCategoryId());
		boolean exceptionCatched = false;
		try {
			accountFacade.findCategory(theCategory.getCategoryId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

	/**
	 * Method for testing the deletion of a non-existent account
	 * 
	 * @throws InternalErrorException
	 */
	@Test
	public void testDeleteCategoryNonExistentCategory()
			throws InternalErrorException {

		boolean exceptionCatched = false;
		try {
			accountFacade.deleteCategory(NON_EXISTENT_CATEGORY_ID);
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

}
