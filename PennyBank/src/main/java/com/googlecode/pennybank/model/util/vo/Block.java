package com.googlecode.pennybank.model.util.vo;

import java.util.List;

/**
 * A block of Type-T entities
 * @author spenap
 * @param <T> The entity type
 */
public class Block<T> {

	private boolean existMore;

	private List<T> contents;

	/**
	 * @return a boolean value indicating if therer are more entities
	 */
	public boolean isExistMore() {
		return existMore;
	}

	/**
	 * @param existMore
	 *            the existMore to set
	 */
	public void setExistMore(boolean existMore) {
		this.existMore = existMore;
	}

	/**
	 * @param existMore
	 * @param contents
	 */
	public Block(boolean existMore, List<T> contents) {
		this.existMore = existMore;
		this.contents = contents;
	}

	/**
	 * @return the contents
	 */
	public List<T> getContents() {
		return contents;
	}

	/**
	 * @param contents
	 *            the contents to set
	 */
	public void setContents(List<T> contents) {
		this.contents = contents;
	}

}
