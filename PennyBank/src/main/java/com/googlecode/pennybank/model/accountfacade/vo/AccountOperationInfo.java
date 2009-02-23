package com.googlecode.pennybank.model.accountfacade.vo;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation.Type;
import com.googlecode.pennybank.model.category.entity.Category;
import java.text.SimpleDateFormat;

/**
 * A class encapsulating the information needed to display an account operation
 *
 * @author spenap
 */
public class AccountOperationInfo {

    private Type type;
    private double amount;
    private Calendar date;
    private String comment;
    private Set<Category> categories = new HashSet<Category>();

    /**
     * Creates a new AccountOperationInfo from an AccountOperation
     *
     * @param accountOperation The AccountOperation
     * @return the AccountOperationInfo
     */
    public static AccountOperationInfo fromAccountOperation(AccountOperation accountOperation) {
        return new AccountOperationInfo(accountOperation.getType(), accountOperation.
                getAmount(), accountOperation.getDate(), accountOperation.
                getComment(), accountOperation.getCategories());
    }

    /**
     * Creates an account operation info with the specified arguments
     *
     * @param type The account type
     * @param amount The amount
     * @param date The operation Date
     * @param comment The operation description
     * @param categories The categories for this operation
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

    /**
     *
     * @return The string representation of the account operation
     */
    @Override
    public String toString() {

        StringBuffer output = new StringBuffer();

        SimpleDateFormat dateFormat = new SimpleDateFormat("[yyyy/MM/dd]");
        output.append(dateFormat.format(date.getTime()));
        output.append(" " + type.toString().
                toUpperCase());
        output.append(":" + amount);
        output.append("\t\"" + comment + "\"");
        return output.toString();
    }
}
