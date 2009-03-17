/**
 * MainContent.java
 * 
 * 02/03/2009
 */
package com.googlecode.pennybank.swing.view.main;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.googlecode.pennybank.swing.view.util.PlatformUtils;

/**
 * 
 * @author spenap
 */
public class MainContent {

	public static JSplitPane getJSPlitPane(MainNavigationPanel navigationPanel,
			JComponent contentPanel) {

		JSplitPane thePane = null;

		if (PlatformUtils.isMacOS()) {
			thePane = MacWidgetFactory.createSplitPaneForSourceList(
					navigationPanel.getSourceList(), contentPanel);
			// TODO: Fix divider location
			thePane.setDividerLocation(180);

		} else {
			thePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
					navigationPanel.getComponent(), contentPanel);
		}

		return thePane;
	}
}
