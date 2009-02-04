package com.googlecode.pennybank.model.category.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;

@Entity
public class Category {

	private Long categoryId;
	private String name;
	private Set<AccountOperation> accountOperations = new HashSet<AccountOperation>();
	private long version;

	public Category() {
	}

	/**
	 * @param name
	 */
	public Category(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Category))
			return false;
		Category theOther = (Category) obj;
		return categoryId.equals(theOther.categoryId)
				&& name.equals(theOther.name)
				&& accountOperations.equals(theOther.accountOperations)
				&& version == theOther.version;
	}

	/**
	 * @return the accountOperations
	 */
	@ManyToMany
	@JoinTable(name = "opCategory", joinColumns = @JoinColumn(name = "categoryId"), inverseJoinColumns = @JoinColumn(name = "accOpId"))
	public Set<AccountOperation> getAccountOperations() {
		return accountOperations;
	}

	/**
	 * @return the categoryId
	 */
	@Id
	@SequenceGenerator(name = "CategoryIdGenerator", sequenceName = "CategoryIdSeq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CategoryIdGenerator")
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the version
	 */
	@Version
	public long getVersion() {
		return version;
	}

	/**
	 * @param accountOperations
	 *            the accountOperations to set
	 */
	public void setAccountOperations(Set<AccountOperation> accountOperations) {
		this.accountOperations = accountOperations;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}
}
