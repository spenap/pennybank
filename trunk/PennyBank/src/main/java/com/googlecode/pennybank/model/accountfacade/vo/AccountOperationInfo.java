package com.googlecode.pennybank.model.accountfacade.vo;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;

public class AccountOperationInfo {

	private Type type;
	private double amount;
	private Calendar date;
	private String comment;
	private Set<Category> categories = new HashSet<Category>();

	/**
	 * @param type
	 * @param amount
	 * @param date
	 * @param comment
	 * @param categories
	 */
	public AccountOperationInfo(Type type, double amount, Calendar date,
			String comment, Set<Category> categories) {
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.comment = comment;
		this.categories = categories;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the categories
	 */
	public Set<Category> getCategories() {
		return categories;
	}
	
}
