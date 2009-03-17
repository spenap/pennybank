/**
 * PlatformUtil.java
 * 
 * 01/03/2009
 */
package com.googlecode.pennybank.swing.view.util;

/**
 * Utility class which helps to tell which OS the application is running on.
 * 
 * @author spenap
 */
public class PlatformUtils {

	/**
	 * Method which tells if we are running on a mac OS
	 * 
	 * @return True if we are running on a Mac, false otherwise
	 */
	public static boolean isMacOS() {
		return com.explodingpixels.util.PlatformUtils.isMac();
	}

	public static boolean isWindows() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isLinux() {
		// TODO Auto-generated method stub
		return true;
	}

}
