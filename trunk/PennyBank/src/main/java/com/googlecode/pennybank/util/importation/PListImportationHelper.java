package com.googlecode.pennybank.util.importation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.util.importation.exceptions.NotYetParsedException;
import com.googlecode.pennybank.util.importation.exceptions.ParseException;

/**
 * Utility class to import from Cashbox PList files.
 * 
 * A Cashbox PList file is a Mac property list file. It stores some account
 * properties, and every account operation made to that account.
 * 
 * There is a method, parseAccountFile, which tries to parse the given file,
 * populating the both the category and account operation lists. It also creates
 * an account representing the one stored in the file. The category list created
 * does not have any hierarchy information
 * 
 * @author spenap
 */
public class PListImportationHelper {

	private String filename;
	private Account account;
	private List<Category> categoriesRead;
	private List<AccountOperation> accountOperationsRead;

	/**
	 * Gets the account from the file.
	 * 
	 * @return The account
	 * @throws NotYetParsedException
	 *             If this method is called before parsing the file
	 */
	public Account getAccount() throws NotYetParsedException {

		if (account == null) {
			throw new NotYetParsedException(filename);
		}
		return account;
	}

	/**
	 * Gets all the account operations from the list
	 * 
	 * @return A list with the account operations
	 * @throws NotYetParsedException
	 *             If this method is called before parsing the file
	 */
	public List<AccountOperation> getAccountOperations()
			throws NotYetParsedException {

		if (accountOperationsRead == null) {
			throw new NotYetParsedException(filename);
		}
		return accountOperationsRead;
	}

	/**
	 * Gets all the categories read from the file
	 * 
	 * @return A list with the categories
	 * @throws NotYetParsedException
	 *             If this method is called before parsing the file
	 */
	public List<Category> getCategories() throws NotYetParsedException {

		if (categoriesRead == null) {
			throw new NotYetParsedException(filename);
		}
		return categoriesRead;
	}

	/**
	 * Parses a file containing information from an account
	 * 
	 * @param filename
	 *            The filename to be parsed
	 * @throws ParseException
	 *             If an error happens while parsing the file
	 */
	public void parseAccountFile(String filename) throws ParseException {

		this.filename = filename;
		categoriesRead = new ArrayList<Category>();
		account = new Account();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(filename);

			NodeList childNodes = document.getDocumentElement().getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {

				if (childNodes.item(j).getNodeType() == Document.ELEMENT_NODE) {

					Node dict = childNodes.item(j);
					NodeList nodeList = dict.getChildNodes();
					String currentKey = "";

					for (int i = 0; i < nodeList.getLength(); i++) {
						Node key = nodeList.item(i);

						if (key.getNodeType() != Document.ELEMENT_NODE) {
							continue;
						}

						if (key.getNodeName().equalsIgnoreCase("key")) {

							currentKey = key.getTextContent();

						} else if (key.getNodeName().equalsIgnoreCase("string")
								&& currentKey.equalsIgnoreCase("account name")) {

							account.setName(key.getTextContent());

						} else if (key.getNodeName().equalsIgnoreCase("real")
								&& currentKey
										.equalsIgnoreCase("final total of account")) {

							String balanceAsString = key.getTextContent();
							account.setBalance(Double
									.parseDouble(balanceAsString));

						} else if (key.getNodeName().equalsIgnoreCase("array")
								&& currentKey.equalsIgnoreCase("transactions")) {

							readAccountOperations(key);
						}
					}
				}
			}

		} catch (SAXException e) {
			throw new ParseException(e);
		} catch (IOException e) {
			throw new ParseException(e);
		} catch (ParserConfigurationException e) {
			throw new ParseException(e);
		}

	}

	private Calendar parseDate(String fullDateAsString) throws ParseException {

		Calendar operationDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {

			String dateAsString = fullDateAsString.substring(0,
					fullDateAsString.indexOf('T'));
			Date date = dateFormat.parse(dateAsString);
			operationDate.setTime(date);
			operationDate.set(Calendar.HOUR_OF_DAY, 0);
			operationDate.set(Calendar.MINUTE, 0);
			operationDate.set(Calendar.SECOND, 0);

		} catch (DOMException e) {
			throw new ParseException(e);
		} catch (java.text.ParseException e) {
			throw new ParseException(e);
		}
		return operationDate;
	}

	private void readAccountOperation(Node item) throws ParseException {

		AccountOperation accountOperation = new AccountOperation();

		NodeList accountOpChunk = item.getChildNodes();
		String currentKey = "";
		boolean hasType = false;

		for (int i = 0; i < accountOpChunk.getLength(); i++) {

			if (accountOpChunk.item(i).getNodeType() != Document.ELEMENT_NODE) {
				continue;
			}

			String textContent = accountOpChunk.item(i).getTextContent();

			if (accountOpChunk.item(i).getNodeName().equalsIgnoreCase("key")) {

				currentKey = textContent;

			} else if (accountOpChunk.item(i).getNodeName().equalsIgnoreCase(
					"date")
					&& currentKey.equalsIgnoreCase("date column")) {

				Calendar operationDate = parseDate(textContent);
				accountOperation.setDate(operationDate);

			} else if (accountOpChunk.item(i).getNodeName().equalsIgnoreCase(
					"string")
					&& currentKey.equalsIgnoreCase("description column")) {

				accountOperation.setComment(textContent);

			} else if (!hasType
					&& accountOpChunk.item(i).getNodeName().equalsIgnoreCase(
							"real")
					&& currentKey.equalsIgnoreCase("deposit column")) {

				double theAmount = Double.parseDouble(textContent);
				if (theAmount != 0) {
					accountOperation.setAmount(theAmount);
					accountOperation.setType(Type.DEPOSIT);
					hasType = true;
				}

			} else if (!hasType
					&& accountOpChunk.item(i).getNodeName().equalsIgnoreCase(
							"real")
					&& currentKey.equalsIgnoreCase("withdrawal column")) {

				double theAmount = Double.parseDouble(textContent);
				if (theAmount != 0) {
					accountOperation.setAmount(theAmount);
					accountOperation.setType(Type.WITHDRAW);
					hasType = true;
				}

			} else if (accountOpChunk.item(i).getNodeName().equalsIgnoreCase(
					"string")
					&& currentKey.equalsIgnoreCase("label")) {

				accountOperation.setCategory(getCategory(textContent));
			}
		}
		accountOperation.setAccount(account);
		accountOperationsRead.add(accountOperation);
	}

	private Category getCategory(String categoryName) {
		for (Category category : categoriesRead) {
			if (category.getName().equals(categoryName))
				return category;
		}
		return new Category(categoryName);
	}

	private void readAccountOperations(Node key) throws ParseException {

		accountOperationsRead = new ArrayList<AccountOperation>();
		NodeList accountOps = key.getChildNodes();
		for (int k = 0; k < accountOps.getLength(); k++) {
			if ((accountOps.item(k).getNodeType() == Document.ELEMENT_NODE)
					&& (accountOps.item(k).getNodeName()
							.equalsIgnoreCase("dict"))) {
				readAccountOperation(accountOps.item(k));
			}
		}
		System.out.println(accountOperationsRead.size() + " operations read");
	}

	public static void main(String args[]) {
		PListImportationHelper helper = new PListImportationHelper();
		String pList = "src/test/resources/CashboxAccountFile.plist";
		try {
			helper.parseAccountFile(pList);
			for (AccountOperation operation : helper.getAccountOperations()) {
				System.out.println(operation);
			}
		} catch (NotYetParsedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
