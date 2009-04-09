package com.googlecode.pennybank;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.controller.actions.UIAction;
import com.googlecode.pennybank.swing.view.main.MainMenuBar;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.PlatformUtils;
import com.googlecode.pennybank.util.PingQuery;

/**
 * Application main class, which creates and shows the GUI
 * 
 * @author spenap
 */
public class App {

	private static List<Category> categories;
	private static boolean databaseReady;
	private static Stack<UIAction> redoableActions;
	private static Stack<UIAction> undoableActions;

	/**
	 * Application entry point
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			initLookAndFeel();

		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
		initializeApp();

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				MainWindow.getInstance().setVisible(true);
			}
		});
	}

	private static void initLookAndFeel() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		if (PlatformUtils.isMacOS()) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name",
					MessageManager.getMessage("Application.Name"));
		} else if (PlatformUtils.isWindows()) {
			// Windows initialization
		} else if (PlatformUtils.isLinux()) {
			// Linux initialization
		}
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}

	private static void initializeApp() {

		if (!PingQuery.Ping()) {
			GuiUtils.error("App.NoDatabaseConnection");
			databaseReady = false;
		}
		databaseReady = true;
		undoableActions = new Stack<UIAction>();
		redoableActions = new Stack<UIAction>();
	}

	/**
	 * Method which allows an user to retrieve the existing categories
	 * 
	 * @return The categories defined
	 */
	public static List<Category> getCategories() {
		if (categories == null) {
			try {
				categories = AccountFacadeDelegateFactory.getDelegate()
						.findAllCategories();
			} catch (InternalErrorException e) {
				e.printStackTrace();
				categories = new ArrayList<Category>();
			}
		}
		return categories;
	}

	/**
	 * Method which tells if the database is ready to be accessed
	 * 
	 * @return the database status
	 */
	public static boolean isDatabaseReady() {
		return databaseReady;
	}

	public static boolean execute(UIAction action) {
		undoableActions.add(action);
		MainMenuBar menuBar = (MainMenuBar) MainWindow.getInstance()
				.getMainMenuBar();
		menuBar.setUndoText(action.getName());
		return action.execute();
	}

	public static void redoAction() {
		UIAction theAction = redoableActions.pop();
		undoableActions.add(theAction);
		MainMenuBar menuBar = (MainMenuBar) MainWindow.getInstance()
				.getMainMenuBar();
		menuBar.setUndoText(theAction.getName());
		theAction.redo();
		if (!redoableActions.isEmpty()) {
			menuBar.setRedoText(redoableActions.peek().getName());
		} else {
			menuBar.setRedoText(null);
		}
	}

	public static void undoAction() {
		UIAction theAction = undoableActions.pop();
		redoableActions.add(theAction);
		MainMenuBar menuBar = (MainMenuBar) MainWindow.getInstance()
				.getMainMenuBar();
		menuBar.setRedoText(theAction.getName());
		theAction.undo();
		if (!undoableActions.isEmpty()) {
			menuBar.setUndoText(undoableActions.peek().getName());
		} else {
			menuBar.setUndoText(null);
		}
	}
}
