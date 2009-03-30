/**
 * 
 */
package com.googlecode.pennybank.util.exportation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * Utility class to export data to CSV format. It is possible to export
 * categories, accounts or account operations.
 * 
 * {@link http://tools.ietf.org/html/rfc4180}
 * 
 * @author spenap
 */
public class CSVExportationHelper {

	public static void main(String[] args) {
		CSVExportationHelper helper = new CSVExportationHelper();
		helper.exportCategories("/tmp/categories.csv");
	}

	private List<Category> categories = null;
	private List<Account> accounts = null;
	private List<AccountOperation> operations = null;

	/**
	 * Creates a new CSV exporter without giving the data to be exported
	 */
	public CSVExportationHelper() {

	}

	/**
	 * Creates a new CSV exporter, providing the data which will be exported
	 * 
	 * @param categories
	 *            The categories to be exported
	 * @param accounts
	 *            The accounts to be exported
	 * @param operations
	 *            The operations to be exported
	 */
	public CSVExportationHelper(List<Category> categories,
			List<Account> accounts, List<AccountOperation> operations) {
		this.categories = categories;
		this.accounts = accounts;
		this.operations = operations;
	}

	/**
	 * Exports the account operations to the given file
	 * 
	 * @param operationsFile
	 *            The file where the operations will be saved
	 */
	public void exportAccountOperations(String operationsFile) {
		List<AccountOperation> toExport = null;
		if (operations == null)
			return;
		toExport = operations;

		saveElements(operationsFile, toExport, AccountOperation.class);
	}

	/**
	 * Exports the accounts to the given file
	 * 
	 * @param accountsFile
	 *            The file where the accounts will be saved
	 */
	public void exportAccounts(String accountsFile) {

		List<Account> toExport = null;
		if (accounts == null)
			return;
		toExport = accounts;

		saveElements(accountsFile, toExport, Account.class);

	}

	/**
	 * Exports the categories to the given file
	 * 
	 * @param categoriesFile
	 *            The file where the categories will be saved
	 */
	public void exportCategories(String categoriesFile) {
		List<Category> toExport = null;

		if (categories == null) {
			try {
				toExport = AccountFacadeDelegateFactory.getDelegate()
						.findAllCategories();
			} catch (InternalErrorException e) {

			}
		} else {
			toExport = categories;
		}

		saveElements(categoriesFile, toExport, Category.class);
	}

	private <T> String generateEntityLine(T element) {
		StringBuilder lineBuilder = new StringBuilder();
		if (element instanceof Category) {
			Category c = (Category) element;
			lineBuilder.append(c.getName() + ";");
			if (c.getParentCategory() != null)
				lineBuilder.append(c.getParentCategory().getName());
		} else if (element instanceof Account) {
			Account a = (Account) element;
			lineBuilder.append(a.getName() + ";");
			lineBuilder.append(a.getUser().getName() + ";");
			lineBuilder.append(a.getBalance());
		} else if (element instanceof AccountOperation) {
			AccountOperation o = (AccountOperation) element;
			lineBuilder.append(o.getType() + ";");
			lineBuilder.append(o.getAmount() + ";");
			lineBuilder.append(o.getDate().getTime().toString() + ";");
			lineBuilder.append(o.getComment() + ";");
			if (o.getCategory() != null)
				lineBuilder.append(o.getCategory().getName());
		}
		return lineBuilder.toString();
	}

	private <T> String generateHeader(Class<T> entity) {
		StringBuilder headerBuilder = new StringBuilder();
		if (entity == Category.class) {
			headerBuilder.append(MessageManager
					.getMessage("CategoryWindow.CategoryName")
					+ ";");
			headerBuilder.append(MessageManager
					.getMessage("CategoryWindow.ParentCategory"));
		} else if (entity == Account.class) {
			headerBuilder.append(MessageManager
					.getMessage("AccountWindow.AccountName")
					+ ";");
			headerBuilder.append(MessageManager
					.getMessage("ProfileWindow.ProfileName")
					+ ";");
			headerBuilder.append(MessageManager
					.getMessage("AccountWindow.Balance"));
		} else if (entity == AccountOperation.class) {
			headerBuilder.append(MessageManager
					.getMessage("AccountOperationWindow.Amount")
					+ ";");
			headerBuilder.append(MessageManager
					.getMessage("AccountOperationWindow.Comment")
					+ ";");
			headerBuilder.append(MessageManager
					.getMessage("AccountOperationWindow.Date")
					+ ";");
			headerBuilder.append(MessageManager
					.getMessage("AccountOperationWindow.Category")
					+ ";");
			headerBuilder.append(MessageManager
					.getMessage("AccountOperationWindow.Description"));
		}
		return headerBuilder.toString();
	}

	private <T> void saveElements(String destinationFile, List<T> toExport,
			Class<T> entity) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(destinationFile));

			// Generate file header
			writer.write(generateHeader(entity));
			writer.newLine();

			// Add elements
			for (T element : toExport) {
				writer.write(generateEntityLine(element));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {

		}
	}
}
