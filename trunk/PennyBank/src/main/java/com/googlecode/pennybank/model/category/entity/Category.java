package com.googlecode.pennybank.model.category.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 * Entity representing a category
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
@Entity
public class Category implements Serializable {

	private Long categoryId;
	private String name;
	private Category parentCategory;
	private long version;

	/**
	 * Constructor without arguments, needed for Hibernate
	 */
	public Category() {
	}

	/**
	 * @param name
	 *            The category name
	 */
	public Category(String name) {
		this.name = name;
		this.setParentCategory(null);
	}

	/**
	 * 
	 * @param name
	 *            The category name
	 * @param parentCategory
	 *            The parent category
	 */
	public Category(String name, Category parentCategory) {
		this.name = name;
		this.setParentCategory(parentCategory);
	}

	/**
	 * 
	 * @param obj
	 *            The object to compare
	 * @return True if the categories are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Category)) {
			return false;
		}
		Category theOther = (Category) obj;
		if ((parentCategory == null && theOther.parentCategory != null)
				|| (parentCategory != null && !parentCategory
						.equals(theOther.parentCategory)))
			return false;
		return categoryId.equals(theOther.categoryId)
				&& name.equals(theOther.name) && version == theOther.version;
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
	 * 
	 * @return the parent category
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "parentCategoryId")
	public Category getParentCategory() {
		return parentCategory;
	}

	/**
	 * @return the version
	 */
	@Version
	public long getVersion() {
		return version;
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

	/**
	 * 
	 * @param parentCategory
	 *            the parent category to set
	 */
	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

}
