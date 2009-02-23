package com.googlecode.pennybank.model.accountoperation.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
 * @author spenap
 */
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
    private Set<Category> categories = new HashSet<Category>();

    /**
     * Constructor without arguments, needed for Hibernate
     */
    public AccountOperation() {
    }

    /**
     * Creates a new account operation
     *
     * @param account The account for the account operation
     * @param type The account operation type
     * @param amount The account operation amount
     * @param date The account operation date
     * @param comment The account operation description
     */
    public AccountOperation(Account account, Type type, double amount,
            Calendar date, String comment) {

        this.account = account;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.comment = comment;
    }

    /**
     *
     * @param obj The object to compare
     * @return True if the account operations are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if ((obj == null) || !(obj instanceof AccountOperation)) {
            return false;
        }
        AccountOperation theOther = (AccountOperation) obj;
        return operationId.equals(theOther.operationId) && amount == theOther.amount && comment.
                equals(theOther.comment) && date.equals(theOther.date) && version == theOther.version;
    }

    /**
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash =
                89 * hash + (this.operationId != null ? this.operationId.
                hashCode() : 0);
        hash =
                89 * hash + (int) (Double.doubleToLongBits(this.amount) ^ (Double.
                doubleToLongBits(this.amount) >>> 32));
        hash = 89 * hash + (this.date != null ? this.date.hashCode() : 0);
        hash = 89 * hash + (this.comment != null ? this.comment.hashCode() : 0);
        hash = 89 * hash + (int) (this.version ^ (this.version >>> 32));
        return hash;
    }

    /**
     * @return the account
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade =
    CascadeType.ALL)
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
     * @return the categories
     */
    @ManyToMany(mappedBy = "accountOperations")
    public Set<Category> getCategories() {

        return categories;
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
    @SequenceGenerator(name = "AccountOperationIdGenerator", sequenceName =
    "AccountOpSeq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator =
    "AccountOperationIdGenerator")
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

        this.amount = amount;
    }

    /**
     * @param categories
     *            the categories to set
     */
    public void setCategories(Set<Category> categories) {

        this.categories = categories;
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

        this.date = date;
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

        StringBuffer output = new StringBuffer();

        SimpleDateFormat dateFormat = new SimpleDateFormat("[yyyy/MM/dd]");
        output.append(dateFormat.format(date.getTime()));
        output.append(" " + type.toString().toUpperCase());
        output.append(":" + amount);
        output.append("\t\"" + comment + "\"");
        return output.toString();
    }
}
