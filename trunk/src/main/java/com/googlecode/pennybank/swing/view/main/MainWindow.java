package com.googlecode.pennybank.swing.view.main;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.widgets.WindowUtils;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.PlatformUtils;

/**
 * The application main window
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private Container mainWindow;
	private MainToolBar toolBar;
	private JSplitPane mainContent;
	private MainStatusBar statusBar;
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
		if (PlatformUtils.isMacOS()) {
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			MacUtils.makeWindowLeopardStyle(getRootPane());
			WindowUtils.createAndInstallRepaintWindowFocusListener(this);
		}

		// Menu Bar
		menuBar = new MainMenuBar();
		setJMenuBar(menuBar);

		// Navigation Panel
		navigationPanel = new MainNavigationPanel();

		// ToolBar
		toolBar = new MainToolBar();

		// Content Panel
		contentPanel = new MainContentPanel();

		// BottomBar
		statusBar = new MainStatusBar();

		// Main Content
		mainContent = MainContent.getJSPlitPane(navigationPanel, contentPanel);

		// Window
		mainWindow = new JPanel(new BorderLayout());
		mainWindow.add(toolBar.getComponent(this), BorderLayout.NORTH);
		mainWindow.add(mainContent, BorderLayout.CENTER);
		mainWindow.add(statusBar.getStatusBar(), BorderLayout.SOUTH);

		setContentPane(mainWindow);
	}

	private void setUp() {
		pack();
		setLocation(100, 50);
	}

	public MainStatusBar getStatusBar() {
		return statusBar;
	}

	public MainToolBar getToolBar() {
		return toolBar;
	}

	public JMenuBar getMainMenuBar() {
		return menuBar;
	}

	public void setUserEnabled(boolean value) {
		((MainMenuBar) menuBar).setUserEnabled(value);
		toolBar.setUserEnabled(value);
	}

	public void setAccountEnabled(boolean value) {
		((MainMenuBar) menuBar).setAccountEnabled(value);
		toolBar.setAccountEnabled(value);
	}
}
