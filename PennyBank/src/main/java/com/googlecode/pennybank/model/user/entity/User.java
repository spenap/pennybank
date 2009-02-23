package com.googlecode.pennybank.model.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.googlecode.pennybank.model.account.entity.Account;

/**
 * Entity encapsulating an user
 *
 * @author spenap
 */
@Entity
public class User {

    private Long userId;
    private String name;
    private List<Account> accounts = new ArrayList<Account>();
    private long version;

    /**
     * Constructor without arguments, needed for Hibernate
     */
    public User() {
    }

    /**
     * Creates an user given a name
     * 
     * @param name
     */
    public User(String name) {
        this.name = name;
    }

    /**
     *
     * @param obj The object to compare
     * @return True if the users are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if ((obj == null) || !(obj instanceof User)) {
            return false;
        }
        User theOther = (User) obj;
        if ((accounts == null && theOther.accounts != null) || !accounts.equals(theOther.accounts)) {
            return false;
        }
        return userId.equals(theOther.userId) && name.equals(theOther.name) && version == theOther.version;
    }

    /**
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.userId != null ? this.userId.hashCode() : 0);
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash =
                37 * hash + (this.accounts != null ? this.accounts.hashCode() : 0);
        hash = 37 * hash + (int) (this.version ^ (this.version >>> 32));
        return hash;
    }

    /**
     * @return the accounts
     */
    @OneToMany(mappedBy = "user", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the user Id
     */
    @Id
    @Column(name = "usrId")
    @SequenceGenerator(name = "UserIdGenerator", sequenceName = "UserIdSeq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator =
    "UserIdGenerator")
    public Long getUserId() {
        return userId;
    }

    /**
     * @return the version
     */
    @Version
    public long getVersion() {
        return version;
    }

    /**
     * @param accounts
     *            the accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(long version) {
        this.version = version;
    }
}
