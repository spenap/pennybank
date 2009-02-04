package com.googlecode.pennybank.model.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.googlecode.pennybank.model.user.entity.User;

@Entity
public class Account {

	private Long accountId;
	private String name;
	private User user;
	private double balance;
	private long version;

	public Account() {

	}

	/**
	 * @param userId
	 *            the userId to set
	 * @param balance
	 *            the balance to set
	 * @param name
	 *            the name to set
	 */
	public Account(User user, double balance, String name) {

		this.user = user;
		this.balance = balance;
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {

		if ((obj == null) || !(obj instanceof Account))
			return false;

		Account theOther = (Account) obj;

		return accountId.equals(theOther.accountId)
				&& user.equals(theOther.user) && name.equals(theOther.name)
				&& balance == theOther.balance && version == theOther.version;
	}

	/**
	 * @return the accountId
	 */
	@Column(name = "accId")
	@SequenceGenerator(name = "AccountIdGenerator", sequenceName = "AccountSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AccountIdGenerator")
	public Long getAccountId() {

		return accountId;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {

		return balance;
	}

	/**
	 * @return the user
	 */
	@JoinColumn(name = "usrId")
	@ManyToOne
	public User getUser() {

		return user;
	}

	/**
	 * @return the version
	 */
	@Version
	public long getVersion() {

		return version;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */

	public void setAccountId(Long accountId) {

		this.accountId = accountId;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(double balance) {

		this.balance = balance;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {

		this.user = user;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(long version) {

		this.version = version;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	@Override
	public String toString() {

		StringBuffer output = new StringBuffer();
		
		output.append("NOMBRE:"+name+"\n");
		output.append("BALANCE:"+balance);	
		
		return output.toString();
	}
}
