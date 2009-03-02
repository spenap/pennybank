package com.googlecode.pennybank.swing.view.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.widgets.WindowUtils;
import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * The application main window
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private Container mainWindow;
	private Component toolBar;
	private JSplitPane mainContent;
	private Component bottomBar;
	private MainNavigationPanel navigationPanel;
	private JComponent contentPanel;
	private JMenuBar menuBar;

	private static MainWindow instance = null;

	/**
	 * Creates the main window of the application
	 * 
	 * @return The main window
	 */
	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}

	private MainWindow() {

		initComponents();
		setUp();
	}

	/**
	 * Retrieves the main content of the application
	 * 
	 * @return The application main content
	 */
	public MainContentPanel getContentPanel() {
		return (MainContentPanel) contentPanel;
	}

	/**
	 * Retrieves the navigation panel of the application
	 * 
	 * @return The application navigation panel
	 */
	public MainNavigationPanel getNavigationPanel() {
		return navigationPanel;
	}

	private void initComponents() {

		// MainWindow Look & Feel
		setTitle(MessageManager.getMessage("MainWindow.Title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MacUtils.makeWindowLeopardStyle(getRootPane());
		WindowUtils.createAndInstallRepaintWindowFocusListener(this);

		// Menu Bar
		menuBar = new MainMenuBar();
		setJMenuBar(menuBar);

		// Navigation Panel
		navigationPanel = new MainNavigationPanel();
		for (Account account : App.getCurrentUser().getAccounts()) {
			navigationPanel.addAccount(account);
		}

		// Content Panel
		contentPanel = new MainContentPanel();

		// ToolBar
		toolBar = MainToolBar.getMainToolBar(this);

		// BottomBar
		bottomBar = MainBottomBar.getBottomBar();

		// Main Content
		mainContent = MacWidgetFactory.createSplitPaneForSourceList(
				navigationPanel.getSourceList(), contentPanel);

		// Window
		mainWindow = new JPanel(new BorderLayout());
		mainWindow.add(toolBar, BorderLayout.NORTH);
		mainWindow.add(mainContent, BorderLayout.CENTER);
		mainWindow.add(bottomBar, BorderLayout.SOUTH);

		setContentPane(mainWindow);
	}

	private void setUp() {
		pack();
		setLocation(100, 50);
	}
}
