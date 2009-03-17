/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import com.explodingpixels.macwidgets.BottomBarSize;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.TriAreaComponent;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.PlatformUtils;

import java.awt.Component;

import javax.swing.JLabel;

/**
 * The main BottomBar for the application
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainStatusBar extends Component {

	private JLabel statusLabel;

	public MainStatusBar() {
		String statusBarText = MessageManager.getMessage("StatusBar.Title");
		if (PlatformUtils.isMacOS()) {
			statusLabel = MacWidgetFactory.createEmphasizedLabel(statusBarText);
		} else {
			statusLabel = new JLabel(statusBarText);
		}
	}

	/**
	 * Gets the BottomBar
	 * 
	 * @return the bottomBar
	 */
	public Component getStatusBar() {
		if (PlatformUtils.isMacOS()) {
			TriAreaComponent bottomBar = MacWidgetFactory
					.createBottomBar(BottomBarSize.SMALL);
			bottomBar.addComponentToCenter(statusLabel);
			return bottomBar.getComponent();
		} else {
			return statusLabel;
		}
	}

	public void setText(String text) {
		statusLabel.setText(text);
	}

	public String getText() {
		return statusLabel.getText();
	}
}
