package com.fs.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = new Properties();

    static {
        String fileName = "config.properties";
        System.out.println("DEBUG: Attempting to load " + fileName);

        try (InputStream is = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            if (is == null) {
                System.err.println("DEBUG: FAILED! " + fileName + " not found in classpath.");
            } else {
                properties.load(is);
                System.out.println("DEBUG: Success! Config loaded. Base URL: " + properties.getProperty("base.url"));
            }
        } catch (Exception e) {
            System.err.println("DEBUG: Exception during config load: " + e.getMessage());
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
}