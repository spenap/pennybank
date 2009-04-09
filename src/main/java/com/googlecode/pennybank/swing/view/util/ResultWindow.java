/**
 * 
 */
package com.googlecode.pennybank.swing.view.util;

/**
 * @author spenap
 *
 */
public interface ResultWindow {

	public enum ResultType {
		OK, CANCEL
	}
	
	public ResultType getResult();
	
}
