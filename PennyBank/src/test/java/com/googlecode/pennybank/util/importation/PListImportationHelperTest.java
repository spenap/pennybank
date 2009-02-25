package com.googlecode.pennybank.util.importation;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.vo.AccountOperationInfo;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.googlecode.pennybank.util.importation.exceptions.NotYetParsedException;
import com.googlecode.pennybank.util.importation.exceptions.ParseException;
import java.util.List;

public class PListImportationHelperTest {

	@Test
	public void testImport() throws ParseException, NotYetParsedException {

		PListImportationHelper importationHelper = new PListImportationHelper();
		importationHelper
				.parseAccountFile("src/test/resources/CashboxAccountFile.plist");

		Account theAccount = importationHelper.getAccount();
		List<AccountOperationInfo> accountOperations = importationHelper
				.getAccountOperations();

		System.out.println(theAccount);
		for (AccountOperationInfo accountOperationInfo : accountOperations) {
			System.out.println(accountOperationInfo);
		}
		assertTrue(false);
	}
}
