/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * An utility class to get localized application messages
 * 
 * @author spenap
 */
public final class MessageManager {

    private static final Locale DEFAULT_LOCALE = new Locale("es", "ES");
    private static final String CONFIGURATION_FILE = "i18n/message";
    private static Locale locale = DEFAULT_LOCALE;
    private static ClassLoader classLoader;


    static {
        classLoader = MessageManager.class.getClassLoader();
        locale = DEFAULT_LOCALE;
    }

    /**
     * Gets the localized string corresponding to the given key
     *
     * @param key The key to search for
     * @return The localized string
     */
    public static String getMessage(String key) {
        ResourceBundle messages = ResourceBundle.getBundle(CONFIGURATION_FILE, getLocale(), classLoader);
        String message;
        try {
            message = messages.getString(key);
        } catch (Exception e) {
            message = key;
        }
        return message;
    }

    /**
     *
     * @return the current locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     *
     * @param locale
     */
    public static void setLocale(Locale locale) {
        MessageManager.locale = locale;
    }
}
