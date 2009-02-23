/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.util;

import java.io.InputStream;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Provides access to the icons used in the application. Hides the
 * specific implementation of icons, requiring only the pathToIcon name
 * to obtain it, instead of a file path.

 * @author spenap
 */
public class IconManager {

    private static ClassLoader classLoader;


    static {
        classLoader = IconManager.class.getClassLoader();
    }

    /**
     * Returns an icon image, given the name of the icon, without
     * file extension.
     *
     * @param name The icon name
     * @return An image icon
     */
    public static ImageIcon getIcon(String name) {

        URL pathToIcon = null;
        InputStream s = null;

        if (name.indexOf(".") > 0) {

            pathToIcon = classLoader.getResource("icons/" + name);
            s = classLoader.getResourceAsStream("icons/" + name);
        } else {
            pathToIcon = classLoader.getResource("icons/" + name + ".png");
            s = classLoader.getResourceAsStream("icons/" + name + ".png");
        }

        return new ImageIcon(pathToIcon);
    }
}
