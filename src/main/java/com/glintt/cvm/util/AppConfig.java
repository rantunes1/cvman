package com.glintt.cvm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Configurations utility class
 */
public class AppConfig {
    private static final String PROPERTIES_FILE = "application.properties";

    private static Properties properties = null;

    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                URL configFile = AppConfig.class.getClassLoader().getResource(PROPERTIES_FILE);
                FileInputStream stream = new FileInputStream(configFile.getFile());
                properties.load(stream);
                stream.close();
            } catch (FileNotFoundException fnfex) {
                Logging.error(fnfex);
            } catch (IOException ioe) {
                Logging.error(ioe);
            }
        }

        return properties;
    }

    private static boolean isKeyDefined(String key) {
        return getProperties().containsKey(key);
    }

    private static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    public static String getString(String key) {
        return (isKeyDefined(key)) ? getProperty(key) : null;
    }

    public static int getInt(String key) {
        return (isKeyDefined(key)) ? Integer.valueOf(getProperty(key)) : -1;
    }

    public static boolean getBoolean(String key) {
        return (isKeyDefined(key)) ? Boolean.valueOf(getProperty(key)) : false;
    }
}
