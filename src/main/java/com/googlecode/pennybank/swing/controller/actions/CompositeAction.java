package com.googlecode.pennybank.swing.controller.actions;

import java.util.List;

/**
 * Action class which allows combining several actions into another one, so
 * undoing, redoing and executing the composite one, will propagate the effects
 * over the inner actions
 * 
 * @author spenap
 */
public class CompositeAction extends GenericAction {

	private List<GenericAction> actions = null;
	private String actionName = null;

	/**
	 * Creates the action with the specified arguments
	 * 
	 * @param actions
	 *            The list of inner actions to be executed
	 * @param actionName
	 *            The name this generic action will have
	 */
	public CompositeAction(List<GenericAction> actions, String actionName) {
		this.actions = actions;
		this.actionName = actionName;
	}

	@Override
	protected boolean doExecute() {
		boolean success = true;
		for (GenericAction action : actions) {
			success &= action.doExecute();
		}
		return success;
	}

	@Override
	protected String doGetName() {
		return actionName;
	}

	@Override
	protected void doRedo() {
		for (GenericAction action : actions) {
			action.doRedo();
		}
	}

	@Override
	protected void doUndo() {
		for (GenericAction action : actions) {
			action.doUndo();
		}
	}

}
