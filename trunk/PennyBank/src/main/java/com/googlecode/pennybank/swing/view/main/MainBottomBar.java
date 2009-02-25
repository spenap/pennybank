/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import com.explodingpixels.macwidgets.BottomBarSize;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.TriAreaComponent;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import java.awt.Component;

/**
 * The main BottomBar for the application
 *
 * @author spenap
 */
@SuppressWarnings("serial")
public class MainBottomBar extends Component {

    /**
     * Gets the BottomBar
     *
     * @return the bottomBar
     */
    public static Component getBottomBar() {
        TriAreaComponent bottomBar = MacWidgetFactory.createBottomBar(BottomBarSize.SMALL);
        bottomBar.addComponentToCenter(MacWidgetFactory.createEmphasizedLabel(MessageManager.getMessage("StatusBar.Title")));
        return bottomBar.getComponent();
    }
}
