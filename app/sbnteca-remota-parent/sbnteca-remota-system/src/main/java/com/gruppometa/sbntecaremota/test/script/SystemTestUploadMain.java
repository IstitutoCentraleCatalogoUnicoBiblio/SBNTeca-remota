package com.gruppometa.sbntecaremota.test.script;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemTestUploadMain {
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(SystemTestUploadMain.class);

	public static void main(String[] args) {
		String scriptParametersManual = "Lancio Script: "
				+ "<nome_script.jar> <path_directory_projects>";
		
		if(args.length == 0) {
			logger.error("Path della directory dei pacchetti "
					+ "da caricare non configurato");
			
			System.err.println("Path della directory dei pacchetti "
					+ "da caricare non configurato"
					+ "\n" + scriptParametersManual);
			
			System.exit(-1);
		}
		
		else if(args.length > 1) {
			logger.error("Numero dei parametri non corretto");
			
			System.err.println("Numero dei parametri non corretto"
					+ "\n" + scriptParametersManual);
			
			System.exit(-1);
		}
		
		SystemTestUpload script = new SystemTestUpload();
		script.uploadProjects(new File(args[0]));
		logger.info("Script terminato");
	}

}
