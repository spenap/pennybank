package com.googlecode.pennybank;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.profile.AddProfileWindow;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.PlatformUtils;

/**
 * Application main class, which creates and shows the GUI
 * 
 * @author spenap
 */
public class App {

	private static User currentUser;
	private static UserFacadeDelegate userFacade;
	private static AccountFacadeDelegate accountFacade;

	public static User getCurrentUser() {
		return currentUser;
	}

	public static AccountFacadeDelegate getAccountFacade() {
		return accountFacade;
	}

	public static UserFacadeDelegate getUserFacade() {
		return userFacade;
	}

	public static void setCurrentUser(User theUser) {
		currentUser = theUser;
	}

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
		try {
			initializeApp();

		} catch (InternalErrorException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}

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
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} else if (PlatformUtils.isWindows()) {
			// Windows initialization
		} else if (PlatformUtils.isLinux()) {
			// Linux initialization
		}
	}

	private static void initializeApp() throws InternalErrorException {
		userFacade = UserFacadeDelegateFactory.getDelegate();
		accountFacade = AccountFacadeDelegateFactory.getDelegate();

		List<User> applicationUsers = userFacade.findUsers();
		if (applicationUsers.size() == 1) {
			currentUser = applicationUsers.get(0);
		} else {
			new AddProfileWindow(null, true).setVisible(true);
			List<User> users = userFacade.findUsers();
			if (users.size() == 1) {
				currentUser = users.get(0);
			}
		}
	}
}
