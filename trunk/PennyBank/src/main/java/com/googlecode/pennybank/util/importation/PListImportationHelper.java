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
 * Utility class to import from Cashbox PList files
 * 
 * @author spenap
 */
public class PListImportationHelper {

	private Account account;
	private List<Category> categoriesRead;
	private List<AccountOperation> accountOperationsRead;

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
			throw new NotYetParsedException();
		}
		return accountOperationsRead;
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

					for (int i = 0; i < nodeList.getLength(); i++) {
						Node key = nodeList.item(i);
						if (key.getNodeType() != Document.ELEMENT_NODE) {
							continue;
						}
						if (key.getNodeName().equalsIgnoreCase("string")) {
							String accountNameCandidate = key.getTextContent();
							if (filename.indexOf(accountNameCandidate) > 0) {
								account.setName(accountNameCandidate);
							}
						}

						if (key.getNodeName().equalsIgnoreCase("real")) {
							String balanceAsString = key.getTextContent();
							account.setBalance(Double
									.parseDouble(balanceAsString));
						}

						if (key.getNodeName().equalsIgnoreCase("array")) {
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

	private void readAccountOperations(Node key) throws ParseException {

		accountOperationsRead = new ArrayList<AccountOperation>();
		NodeList accountOps = key.getChildNodes();
		for (int k = 0; k < accountOps.getLength(); k++) {
			if (accountOps.item(k).getNodeType() == Document.ELEMENT_NODE) {
				readAccountOperation(accountOps.item(k));
			}
		}
		System.out.println(accountOperationsRead.size() + " operations read");
	}

	private void readAccountOperation(Node item) throws ParseException {

		if (!item.getNodeName().equalsIgnoreCase("dict")) {
			return;
		}
		AccountOperation accountOperation = new AccountOperation();
		boolean isWithdrawal = false;
		double depositAmount = 0;
		double withdrawalAmount = 0;

		NodeList accountOpChunk = item.getChildNodes();
		for (int i = 0; i < accountOpChunk.getLength(); i++) {
			if (accountOpChunk.item(i).getNodeType() == Document.ELEMENT_NODE) {

				if (accountOpChunk.item(i).getNodeName().equalsIgnoreCase(
						"date")) {
					String fullDateAsString = accountOpChunk.item(i)
							.getTextContent();
					Calendar operationDate = parseDate(fullDateAsString);
					accountOperation.setDate(operationDate);
				} else if (accountOpChunk.item(i).getNodeName()
						.equalsIgnoreCase("string")) {
					String comment = accountOpChunk.item(i).getTextContent();
					accountOperation.setComment(comment);
				} else if (accountOpChunk.item(i).getNodeName()
						.equalsIgnoreCase("real")) {
					String amountAsString = accountOpChunk.item(i)
							.getTextContent();
					if (!isWithdrawal) {
						depositAmount = Double.parseDouble(amountAsString);
						isWithdrawal = true;
					} else {
						withdrawalAmount = Double.parseDouble(amountAsString);
					}
				}
			}
		}
		if (withdrawalAmount == 0) {
			accountOperation.setType(Type.DEPOSIT);
			accountOperation.setAmount(depositAmount);
		} else {
			accountOperation.setType(Type.WITHDRAW);
			accountOperation.setAmount(withdrawalAmount);
		}

		accountOperation.setAccount(account);
		accountOperationsRead.add(accountOperation);
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

	/**
	 * Gets the account from the file
	 * 
	 * @return The account
	 * @throws NotYetParsedException
	 *             If this method is called before parsing the file
	 */
	public Account getAccount() throws NotYetParsedException {

		if (account == null) {
			throw new NotYetParsedException();
		}
		return account;
	}
}
