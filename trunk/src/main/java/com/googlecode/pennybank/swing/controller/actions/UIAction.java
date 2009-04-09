/**
 * 
 */
package com.googlecode.pennybank.swing.controller.actions;

/**
 * This interface defines the methods needed to implement the Command Pattern.
 * It defines methods for executing, undoing, redoing the action, and obtaining
 * the action name.
 * 
 * @author spenap
 */
public interface UIAction {

	/**
	 * Enumerate defining the different outputs from an action
	 * 
	 * @author spenap
	 */
	public enum ActionResult {
		SUCCESS, FAIL
	}

	/**
	 * This method allows the execution of the action.
	 */
	public boolean execute();

	/**
	 * This method allows undoing the action
	 */
	public void undo();

	/**
	 * This method allows redoing the action
	 */
	public void redo();

	/**
	 * This method allows obtaining the action name, which will be used as its
	 * description in the user interface
	 * 
	 * @return The string representing the action name
	 */
	public String getName();
}
