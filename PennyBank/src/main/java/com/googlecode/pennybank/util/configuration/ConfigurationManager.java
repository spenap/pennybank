package com.googlecode.pennybank.util.configuration;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;

import com.googlecode.pennybank.model.util.exceptions.MissingConfigurationParameterException;

@SuppressWarnings("unchecked")
public class ConfigurationManager {

	private static final String JNDI_PREFIX = "java:comp/env/";

	private static final String CONFIGURATION_FILE = "ConfigurationParameters.properties";

	private static boolean usesJNDI;

	private static Map parameters;

	static {

		try {

			/* Read property file (if exists). */
			Class configurationManagerClass = ConfigurationManager.class;
			ClassLoader classLoader = configurationManagerClass
					.getClassLoader();
			InputStream inputStream = classLoader
					.getResourceAsStream(CONFIGURATION_FILE);
			Properties properties = new Properties();
			properties.load(inputStream);
			inputStream.close();

			/* We have been able to read the file. */
			usesJNDI = false;
			System.out
					.println(">>>> Loading the configuration parameters from '"
							+ CONFIGURATION_FILE + "' file");

			/*
			 * We use a "HashMap" instead of a "HashTable" because HashMap's
			 * methods are not synchronized (so they are faster), and the
			 * parameters are only read.
			 */
			parameters = new HashMap(properties);

		} catch (Exception e) {

			/* We assume configuration with JNDI. */
			usesJNDI = true;
			System.out.println(">>>> Reading configuration using JNDI");

			/*
			 * We use a synchronized map because it will be filled by using a
			 * lazy strategy.
			 */
			parameters = Collections.synchronizedMap(new HashMap());

		}

	}

	private ConfigurationManager() {
	}

	public static String getParameter(String name)
			throws MissingConfigurationParameterException {

		String value = (String) parameters.get(name);

		if (value == null) {

			if (usesJNDI) {

				try {
					InitialContext initialContext = new InitialContext();

					value = (String) initialContext.lookup(JNDI_PREFIX + name);
					parameters.put(name, value);
				} catch (Exception e) {
					throw new MissingConfigurationParameterException(name);
				}

			} else {

				throw new MissingConfigurationParameterException(name);

			}

		}

		return value;

	}

}
