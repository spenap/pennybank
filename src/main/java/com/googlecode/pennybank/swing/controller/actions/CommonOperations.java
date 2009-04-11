/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

import java.util.List;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.NegativeAmountException;
import com.googlecode.pennybank.model.util.vo.Block;
import com.googlecode.pennybank.swing.view.util.GuiUtils;

/**
 * TODO Fix exception messages
 * 
 * @author spenap
 */
public class CommonOperations {

	public static Account createAccount(Account theAccount) {
		Account createdAccount = null;
		try {
			createdAccount = AccountFacadeDelegateFactory.getDelegate()
					.createAccount(theAccount);
		} catch (InstanceNotFoundException e) {
			GuiUtils.error("AccountWindow.CreateAccount.Failure.NotFound");
		} catch (InternalErrorException e) {
			GuiUtils.error("AccountWindow.CreateAccount.Failure.Generic");
		}
		return createdAccount;
	}

	public static AccountOperation createAccountOperation(
			AccountOperation theAccountOperation) {
		AccountOperation createdOperation = null;
		try {
			switch (theAccountOperation.getType()) {
			case DEPOSIT:
				createdOperation = AccountFacadeDelegateFactory
						.getDelegate()
						.addToAccount(
								theAccountOperation.getAccount().getAccountId(),
								theAccountOperation.getAmount(),
								theAccountOperation.getComment(),
								theAccountOperation.getDate(),
								theAccountOperation.getCategory());
				break;
			case WITHDRAW:
				createdOperation = AccountFacadeDelegateFactory
						.getDelegate()
						.withdrawFromAccount(
								theAccountOperation.getAccount().getAccountId(),
								theAccountOperation.getAmount(),
								theAccountOperation.getComment(),
								theAccountOperation.getDate(),
								theAccountOperation.getCategory());
				break;
			default:
				break;
			}
		} catch (InternalErrorException ex) {
			GuiUtils.error("UserWindow.RestoreUser.Failure.Generic");
		} catch (InstanceNotFoundException ex) {
			GuiUtils.error("UserWindow.RestoreUser.Failure.NotFound");
		} catch (NegativeAmountException e) {
			GuiUtils.error("UserWindow.RestoreUser.Failure.Negative");
		}
		return createdOperation;
	}

	public static User createUser(User theUser) {
		User createdUser = null;
		try {
			createdUser = UserFacadeDelegateFactory.getDelegate().createUser(
					theUser);
		} catch (InternalErrorException e) {
			GuiUtils.error("UserWindow.CreateUser.Failure.Generic");
		}
		return createdUser;
	}

	public static void removeAccount(Account theAccount) {
		try {
			AccountFacadeDelegateFactory.getDelegate().deleteAccount(
					theAccount.getAccountId());
		} catch (InstanceNotFoundException ex) {
			GuiUtils.error("AccountWindow.DeleteAccount.Failure.NotFound");
		} catch (InternalErrorException ex) {
			GuiUtils.error("AccountWindow.DeleteAccount.Failure.Generic");
		}
	}

	public static void removeAccountOperation(
			AccountOperation theAccountOperation) {
		try {
			AccountFacadeDelegateFactory.getDelegate().deleteAccountOperation(
					theAccountOperation.getOperationIdentifier());
		} catch (InstanceNotFoundException e) {
			GuiUtils.error("AccountOperationWindow.Delete.Failure.NotFound");
		} catch (InternalErrorException e) {
			e.printStackTrace();
			GuiUtils.error("AccountOperationWindow.Delete.Failure.Generic");
		}
	}

	public static void removeUser(User theUser) {
		try {
			UserFacadeDelegateFactory.getDelegate().deleteUser(
					theUser.getUserId());
		} catch (InstanceNotFoundException e) {
			GuiUtils.error("UserWindow.DeleteUser.Failure.NotFound");
		} catch (InternalErrorException e) {
			GuiUtils.error("UserWindow.DeleteUser.Failure.Generic");
		}
	}

	public static void updateAccount(Account theAccount) {
		try {
			AccountFacadeDelegateFactory.getDelegate()
					.updateAccount(theAccount);
		} catch (InstanceNotFoundException e) {
			GuiUtils.error("AccountWindow.UpdateAccount.Failure.NotFound");
		} catch (InternalErrorException e) {
			GuiUtils.error("AccountWindow.UpdateAccount.Failure.Generic");
		}
	}

	public static void updateAccountOperation(
			AccountOperation theAccountOperation) {
		try {
			AccountFacadeDelegateFactory.getDelegate().updateAccountOperation(
					theAccountOperation);
		} catch (InstanceNotFoundException e) {
			GuiUtils.error("AccountOperationWindow.Operate.Failure.NotFound");
		} catch (InternalErrorException e) {
			e.printStackTrace();
			GuiUtils.warn("AccountOperationWindow.Operate.Failure.Generic");
		}
	}

	public static void updateUser(User theUser) {
		try {
			UserFacadeDelegateFactory.getDelegate().updateUser(theUser);
		} catch (InstanceNotFoundException e) {
			GuiUtils.warn("UserWindow.EditUser.Failure.NotFound");
		} catch (InternalErrorException e) {
			GuiUtils.error("UserWindow.EditUser.Failure.Generic");
		}
	}

	public static List<AccountOperation> saveAccountState(Account theAccount) {
		try {
			Long opCount = AccountFacadeDelegateFactory.getDelegate()
					.getOperationsCount(theAccount.getAccountId());
			Block<AccountOperation> operationsToDelete = AccountFacadeDelegateFactory
					.getDelegate().findAccountOperations(
							theAccount.getAccountId(), 0, opCount.intValue());
			for (AccountOperation operationToDelete : operationsToDelete
					.getContents()) {
				double balance = theAccount.getBalance();
				switch (operationToDelete.getType()) {
				case DEPOSIT:
					theAccount.setBalance(balance
							- operationToDelete.getAmount());
					break;
				case WITHDRAW:
					theAccount.setBalance(balance
							+ operationToDelete.getAmount());
					break;
				default:
					break;
				}
			}
			return operationsToDelete.getContents();
		} catch (InstanceNotFoundException e) {

		} catch (InternalErrorException e) {

		}
		return null;
	}
}
