package com.gruppometa.magteca.frontend;


import java.io.IOException;
import java.util.Properties;

public class BuildNumber {

    protected static Properties properties = null;

    public static String  getBuildNumber(){
        if(properties==null){
            properties = new Properties();
            try {
                properties.load(BuildNumber.class.getResourceAsStream("/build.properties"));
            } catch (IOException e) {
                ;
            }
        }
        if(properties!=null)
            return properties.getProperty("buildNumber");
        else
            return "";
    }
}