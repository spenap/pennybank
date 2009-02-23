package com.googlecode.pennybank;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * Application main class, which creates and shows the GUI
 *
 * @author spenap
 */
public class App {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User theUser) {
        currentUser = theUser;
    }

    /**
     * Application entry point
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", MessageManager.
                    getMessage("Application.Name"));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        try {
            UserFacadeDelegate userFacade =
                    UserFacadeDelegateFactory.getDelegate();
            List<User> applicationUsers = userFacade.findUsers();
            if (applicationUsers.size() == 1) {
                currentUser = applicationUsers.get(0);
            }

        } catch (InternalErrorException ex) {
            Logger.getLogger(App.class.getName()).
                    log(Level.SEVERE, null, ex);
        }



        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MainWindow.getInstance().
                        setVisible(true);
            }
        });
    }
}
