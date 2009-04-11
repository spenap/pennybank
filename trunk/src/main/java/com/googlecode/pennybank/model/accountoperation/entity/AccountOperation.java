package com.googlecode.pennybank.model.accountoperation.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.category.entity.Category;

/**
 * Entity encapsulation an account operation
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "AccountOp")
public class AccountOperation implements Serializable {

	/**
	 * The account operation type
	 */
	public enum Type {

		/**
		 * If the operation is a deposit
		 */
		DEPOSIT,
		/**
		 * If the operation is a withdrawal
		 */
		WITHDRAW
	}

	private Long operationId;
	private Account account;
	private Type type;
	private double amount;
	private Calendar date;
	private String comment;
	private long version;
	private Category category;

	/**
	 * Constructor without arguments, needed for Hibernate
	 */
	public AccountOperation() {
	}

	/**
	 * Creates a new account operation
	 * 
	 * @param account
	 *            The account for the account operation
	 * @param type
	 *            The account operation type
	 * @param amount
	 *            The account operation amount
	 * @param date
	 *            The account operation date
	 * @param comment
	 *            The account operation description
	 */
	public AccountOperation(Account account, Type type, double amount,
			Calendar date, String comment) {

		this.account = account;
		this.type = type;
		this.amount = amount;
		this.date = Calendar.getInstance();
		this.date.setTime(date.getTime());
		this.comment = comment;
		this.category = null;
	}

	/**
	 * Create a new account operation classified by a category
	 * 
	 * @param account
	 *            The account for the account operation
	 * @param type
	 *            The account operation type
	 * @param amount
	 *            The account operation amount
	 * @param date
	 *            The account operation date
	 * @param comment
	 *            The account operation description
	 * @param category
	 *            The account operation category
	 */
	public AccountOperation(Account account, Type type, double amount,
			Calendar date, String comment, Category category) {

		this.account = account;
		this.type = type;
		this.amount = amount;
		this.date = Calendar.getInstance();
		this.date.setTime(date.getTime());
		this.comment = comment;
		this.category = category;
	}

	/**
	 * 
	 * @param obj
	 *            The object to compare
	 * @return True if the account operations are the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {

		if ((obj == null) || !(obj instanceof AccountOperation)) {
			return false;
		}
		AccountOperation theOther = (AccountOperation) obj;

		if ((category == null && theOther.category != null)
				|| !category.equals(theOther.category)) {
			return false;
		}

		return operationId.equals(theOther.operationId)
				&& account.equals(theOther.account)
				&& amount == theOther.amount
				&& comment.equals(theOther.comment)
				&& date.equals(theOther.date) && version == theOther.version;
	}

	/**
	 * @return the account
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "accId")
	public Account getAccount() {

		return account;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {

		return amount;
	}

	/**
	 * @return the category
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	public Category getCategory() {

		return category;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {

		return comment;
	}

	/**
	 * @return the date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {

		return date;
	}

	/**
	 * @return the operationIdentifier
	 */
	@Id
	@Column(name = "accOpId")
	@SequenceGenerator(name = "AccountOperationIdGenerator", sequenceName = "AccountOpSeq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AccountOperationIdGenerator")
	public Long getOperationIdentifier() {

		return operationId;
	}

	/**
	 * @return the type
	 */
	@Enumerated(EnumType.ORDINAL)
	public Type getType() {

		return type;
	}

	/**
	 * @return the version
	 */
	@Version
	public long getVersion() {

		return version;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(Account account) {

		this.account = account;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {

		if (this.amount != 0 && this.account != null) {
			double difference = this.amount - amount;
			switch (type) {
			case DEPOSIT:
				account.setBalance(account.getBalance() - difference);
				break;
			case WITHDRAW:
				account.setBalance(account.getBalance() + difference);
				break;
			default:
				break;
			}
		}
		this.amount = amount;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {

		this.category = category;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {

		this.comment = comment;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Calendar date) {

		this.date = Calendar.getInstance();
		this.date.setTime(date.getTime());
	}

	/**
	 * @param operationIdentifier
	 *            the operationIdentifier to set
	 */
	public void setOperationIdentifier(Long operationIdentifier) {

		this.operationId = operationIdentifier;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {

		this.type = type;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(long version) {

		this.version = version;
	}

	/**
	 * 
	 * @return The string representation of the account operation
	 */
	@Override
	public String toString() {

		StringBuilder output = new StringBuilder();

		SimpleDateFormat dateFormat = new SimpleDateFormat("[yyyy/MM/dd]");
		output.append(dateFormat.format(date.getTime()));
		output.append(" " + type.toString().toUpperCase());
		output.append(":" + amount);
		output.append("\t\"" + comment + "\"");
		return output.toString();
	}
}
