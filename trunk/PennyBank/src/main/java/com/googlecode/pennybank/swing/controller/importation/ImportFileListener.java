/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.importation;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.pennybank.swing.view.importation.ShowImportationResultsWindow;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.util.importation.PListImportationHelper;
import com.googlecode.pennybank.util.importation.exceptions.ImportationException;
import com.googlecode.pennybank.util.importation.exceptions.NotYetParsedException;
import com.googlecode.pennybank.util.importation.exceptions.ParseException;

/**
 * Listener who allows to import a financial file
 * 
 * @author spenap
 */
public class ImportFileListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

		FileDialog fileChooser = new FileDialog(MainWindow.getInstance());
		fileChooser.setMode(FileDialog.LOAD);
		fileChooser.setVisible(true);

		if (fileChooser.getDirectory() == null)
			return;
		StringBuilder pathToFile = new StringBuilder(fileChooser.getDirectory());
		pathToFile.append(fileChooser.getFile());

		if (pathToFile.toString().toLowerCase().endsWith("plist")) {
			try {
				importPlist(pathToFile.toString());
			} catch (ImportationException ex) {
				GuiUtils.error("Importation.Failure");
			}
		} else {
			GuiUtils.error("Importation.Failure.NotSupported");
		}
	}

	private void importPlist(String pathToFile) throws ImportationException {
		PListImportationHelper importationHelper = new PListImportationHelper();
		try {
			importationHelper.parseAccountFile(pathToFile.toString());
			ShowImportationResultsWindow window = new ShowImportationResultsWindow(
					MainWindow.getInstance(), importationHelper.getAccount(),
					importationHelper.getCategories(), importationHelper
							.getAccountOperations());
			window.setVisible(true);
		} catch (ParseException ex) {
			Logger.getLogger(ImportFileListener.class.getName()).log(
					Level.SEVERE, null, ex);
			throw ex;
		} catch (NotYetParsedException ex) {
			Logger.getLogger(ImportFileListener.class.getName()).log(
					Level.SEVERE, null, ex);
			throw ex;
		}
	}
}
