package com.gruppometa.sbntecaremota.test.script;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemTestScriptMain {
	
	// logger 
	private static Logger logger = LoggerFactory.getLogger(SystemTestScriptMain.class);
	
	

	public static void main(String[] args) {
		// lettura dei parametri
		Properties configProperties = new Properties();
		String scriptParametersManual = "Lancio Script: <nome_script.jar> [<config.properties>]";
		
		try {
			if(args.length == 0) {
				InputStream stream = SystemTestScriptMain.class.getClassLoader().
						getResourceAsStream("system_test.properties");
				
				configProperties.load(stream);
				stream.close();
			}
			
			else if(args.length == 1) {
				InputStream stream = new FileInputStream(args[0]);
				configProperties.load(stream);
				stream.close();
			}
			
			else {
				logger.error("Numero di parametri non corretto dei parametri");
				System.err.println("Numero di parametri non corretto dei parametri\n" + scriptParametersManual);
				System.exit(-1);
			}
			
			SystemTestScript script = new SystemTestScript(configProperties);
			script.createRealSystemTest();
			logger.info("Script terminato");
				
			
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
