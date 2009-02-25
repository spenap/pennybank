package com.googlecode.pennybank.swing.view.main;

import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.widgets.WindowUtils;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * The application main Window
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

    /**
     * Creates the main Window of the aplication
     * 
     * @return The main Window
     */
    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    public MainContentPanel getContentPanel() {
        return (MainContentPanel) contentPanel;
    }

    public MainNavigationPanel getNavigationPanel() {
        return navigationPanel;
    }

    private static MainWindow instance = null;

    private MainWindow() {

        initComponents();
        setUp();
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
