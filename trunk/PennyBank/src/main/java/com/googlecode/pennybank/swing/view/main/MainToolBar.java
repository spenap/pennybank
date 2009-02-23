/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.TriAreaComponent;
import com.googlecode.pennybank.swing.controller.account.AddAccountListener;
import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The main ToolBar for the application
 * 
 * @author spenap
 */
public class MainToolBar extends Component {

    /**
     * Gets the ToolBar
     * 
     * @param mainFrame the frame to insert the toolBar into
     * @return the toolBar
     */
    public static Component getMainToolBar(JFrame mainFrame) {

        TriAreaComponent toolBar = MacWidgetFactory.createUnifiedToolBar();

        toolBar.installWindowDraggerOnWindow(mainFrame);

        // Account buttons
        JButton addAccountButton = new JButton(MessageManager.getMessage("MainToolbar.Accounts.AddAccount"),
                IconManager.getIcon("toolbar_add_account"));
        addAccountButton.addActionListener(new AddAccountListener());
        JButton removeAccountButton = new JButton(MessageManager.getMessage("MainToolbar.Accounts.RemoveAccount"),
                IconManager.getIcon("toolbar_del_account"));
        removeAccountButton.addActionListener(new RemoveAccountListener());

        // Account operation buttons
        JButton addToAccountButton = new JButton(MessageManager.getMessage("MainToolbar.Accounts.AddToAccount"),
                IconManager.getIcon("toolbar_deposit"));
        JButton withdrawFromAccountButton = new JButton(MessageManager.getMessage("MainToolbar.Accounts.WithdrawFromAccount"),
                IconManager.getIcon("toolbar_withdraw"));


        // Preferences button
        JButton preferencesButton = new JButton(MessageManager.getMessage("MainToolbar.Preferences"));
        Icon preferencesIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                "NSImage://NSPreferencesGeneral").getScaledInstance(32, 32,
                Image.SCALE_SMOOTH));
        preferencesButton.setIcon(preferencesIcon);

        // Left side buttons
        toolBar.addComponentToLeft(MacButtonFactory.makeUnifiedToolBarButton(addAccountButton));
        toolBar.addComponentToLeft(MacButtonFactory.makeUnifiedToolBarButton(removeAccountButton));
        toolBar.addComponentToLeft(MacButtonFactory.makeUnifiedToolBarButton(addToAccountButton));
        toolBar.addComponentToLeft(MacButtonFactory.makeUnifiedToolBarButton(withdrawFromAccountButton));

        // Right side buttons
        toolBar.addComponentToRight(MacButtonFactory.makeUnifiedToolBarButton(preferencesButton));

        return toolBar.getComponent();
    }
}
