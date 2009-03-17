package com.googlecode.pennybank.util.importation;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.util.importation.exceptions.NotYetParsedException;
import com.googlecode.pennybank.util.importation.exceptions.ParseException;

public class PListImportationHelperTest {

	@Test
	public void testImport() throws ParseException, NotYetParsedException {

		PListImportationHelper importationHelper = new PListImportationHelper();
		importationHelper
				.parseAccountFile("src/test/resources/CashboxAccountFile.plist");

		Account theAccount = importationHelper.getAccount();
		List<AccountOperation> accountOperations = importationHelper
				.getAccountOperations();

		System.out.println(theAccount);
		for (AccountOperation operation : accountOperations) {
			System.out.println(operation);
		}
		assertTrue(true);
	}
}
