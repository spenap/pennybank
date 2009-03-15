/**
 * 
 */
package com.googlecode.pennybank.swing.view.util;

import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.messagebox.MessageBox;
import com.googlecode.pennybank.swing.view.messagebox.MessageBox.MessageType;

/**
 * @author spenap
 * 
 */
public class GuiUtils {

	public static void warn(String prefix) {
		MessageBox messageBox = new MessageBox(MainWindow.getInstance(),
				MessageManager.getMessage(prefix + ".Title"), MessageManager
						.getMessage(prefix + ".Description"),
				MessageType.INFORMATION);
		messageBox.setVisible(true);
	}

	public static void info(String prefix) {
		MessageBox messageBox = new MessageBox(MainWindow.getInstance(),
				MessageManager.getMessage(prefix + ".Title"), MessageManager
						.getMessage(prefix + ".Description"),
				MessageType.INFORMATION);
		messageBox.setVisible(true);
	}

	public static void error(String prefix) {
		MessageBox messageBox = new MessageBox(MainWindow.getInstance(),
				MessageManager.getMessage(prefix + ".Title"), MessageManager
						.getMessage(prefix + ".Description"), MessageType.ERROR);
		messageBox.setVisible(true);
	}

	public static MessageBox.ResultType confirm(String prefix) {
		MessageBox messageBox = new MessageBox(MainWindow.getInstance(),
				MessageManager.getMessage(prefix + ".Title"), MessageManager
						.getMessage(prefix + ".Description"), MessageType.YESNO);
		messageBox.setVisible(true);
		return messageBox.getWindowResult();
	}

}
