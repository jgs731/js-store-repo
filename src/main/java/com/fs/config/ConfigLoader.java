package com.fs.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = new Properties();

    static {
        String fileName = "config.properties";
        System.out.println("Loading " + fileName);

        try (InputStream is = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            if (is == null) {
                System.err.println(fileName + " not found, please investigate");
            } else {
                properties.load(is);
            }
        } catch (Exception e) {
            System.err.println("DEBUG: Exception during config load: " + e.getMessage());
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
}