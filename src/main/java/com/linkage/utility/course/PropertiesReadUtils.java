package com.linkage.utility.course;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesReadUtils {
	
    public static String readConfigFileProp(String propFileName, String propName) {
        try {
            ClassPathResource resource = new ClassPathResource(propFileName);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.getProperty(propName, "");
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

}
