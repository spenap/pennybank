package com.googlecode.pennybank.model.cascadebehavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.vo.Block;

/**
 * Class which tests the behavior of the Category entity when performing
 * cascading updates or deletes
 * 
 * @author spenap
 * 
 */
public class CategoryCascade {

	private static final double BALANCE = 1000.0;
	private static final double amount = 100;
	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;
	private static User theUser;
	private static Account theAccount;
	private static Category childCategory;
	private static Category parentCategory;
	private static AccountOperation accountOperation;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();

		theUser = new User("CategoryCascade Test");
		theUser = userFacade.createUser(theUser);

		theAccount = new Account(theUser, BALANCE, "CategoryCascade Test");
		theAccount = accountFacade.createAccount(theAccount);

		parentCategory = new Category("CategoryCascade Test");
		parentCategory = accountFacade.createCategory(parentCategory);
		childCategory = new Category("CategoryCascade Test", parentCategory);
		childCategory = accountFacade.createCategory(childCategory);

		accountFacade.addToAccount(theAccount.getAccountId(), amount,
				"CategoryCascade Test", Calendar.getInstance(), parentCategory);
		Block<AccountOperation> block = accountFacade.findAccountOperations(
				theAccount.getAccountId(), 0, 1);
		accountOperation = block.getContents().get(0);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		accountFacade.deleteAccount(theAccount.getAccountId());
		accountFacade.deleteCategory(childCategory.getCategoryId());
		userFacade.deleteUser(theUser.getUserId());
	}

	/**
	 * Tests the behavior on update
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testCategoryOnUpdate() throws InstanceNotFoundException,
			InternalErrorException {

		// Modify the parent category
		parentCategory.setName("Updated Name");
		parentCategory = accountFacade.updateCategory(parentCategory);

		// Check that the child category still exists and has its reference set
		// to null
		assertEquals(parentCategory, childCategory.getParentCategory());

		// Check that the account operation has been updated and has its
		// reference set to null
		assertEquals(parentCategory, accountOperation.getCategory());
	}

	/**
	 * Tests the behavior on delete
	 * 
	 * @throws InstanceNotFoundException
	 * @throws InternalErrorException
	 */
	@Test
	public void testCategoryOnDelete() throws InstanceNotFoundException,
			InternalErrorException {

		// Delete the parent category
		accountFacade.deleteCategory(parentCategory.getCategoryId());

		// Check that the child category still exists and has its reference set
		// to null
		assertTrue(childCategory.getParentCategory() == null);

		// Check that the account operation has been updated and has its
		// reference set to null
		assertTrue(accountOperation.getCategory() == null);

	}
}
