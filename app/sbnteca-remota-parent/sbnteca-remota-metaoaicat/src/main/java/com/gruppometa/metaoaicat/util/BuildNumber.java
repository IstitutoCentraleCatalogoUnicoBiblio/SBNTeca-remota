package com.gruppometa.metaoaicat.util;


import java.io.IOException;
import java.util.Properties;

public class BuildNumber {

    protected static Properties properties = null;

    public static String  getBuildNumber(){
        if(properties==null){
            properties = new Properties();
            try {
                properties.load(BuildNumber.class.getResourceAsStream("/META-INF/build-info.properties"));
            } catch (IOException e) {
                ;
            }
        }
        if(properties!=null)
            return properties.getProperty("build.version")+ " "+properties.getProperty("build.time");
        else
            return "";
    }
}
