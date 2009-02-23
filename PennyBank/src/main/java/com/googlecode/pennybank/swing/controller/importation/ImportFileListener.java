/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.importation;

import com.googlecode.pennybank.model.accountfacade.vo.AccountOperationInfo;
import com.googlecode.pennybank.model.util.vo.Block;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.util.importation.PListImportationHelper;
import com.googlecode.pennybank.util.importation.exceptions.NotYetParsedException;
import com.googlecode.pennybank.util.importation.exceptions.ParseException;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author spenap
 */
public class ImportFileListener implements ActionListener {

    private FileType type;

    public enum FileType {

        PLIST
    }

    public ImportFileListener(FileType fileType) {
        this.type = fileType;
    }

    public void actionPerformed(ActionEvent e) {

        FileDialog fileChooser = new FileDialog(MainWindow.getInstance());
        fileChooser.setMode(FileDialog.LOAD);
        fileChooser.setVisible(true);

        StringBuffer pathToFile = new StringBuffer(fileChooser.getDirectory());
        pathToFile.append(fileChooser.getFile());

        PListImportationHelper importationHelper = new PListImportationHelper();
        try {
            importationHelper.parseAccountFile(pathToFile.toString());
            List<AccountOperationInfo> accountOperations = importationHelper.
                    getAccountOperations();
            MainWindow.getInstance().
                    getContentPanel().
                    setAccountOperations(new Block<AccountOperationInfo>(false, accountOperations));
        } catch (ParseException ex) {
            Logger.getLogger(ImportFileListener.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (NotYetParsedException ex) {
            Logger.getLogger(ImportFileListener.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
