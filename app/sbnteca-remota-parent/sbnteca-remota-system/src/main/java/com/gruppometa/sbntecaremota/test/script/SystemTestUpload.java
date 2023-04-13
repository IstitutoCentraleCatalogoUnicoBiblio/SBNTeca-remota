package com.gruppometa.sbntecaremota.test.script;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.gruppometa.sbntecaremota.objects.db.DBTecaUser;
import com.gruppometa.sbntecaremota.objects.db.DBTecaUserDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.objects.json.JsonUploadReport;
import com.gruppometa.sbntecaremota.restweb.RestServices;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.Utility;

public class SystemTestUpload {
	
	// contesto spring
	ApplicationContext context;
	
	// servizi rest
	private RestServices services;
	
	// dao utenti
	private DBTecaUserDao userDao;
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(SystemTestUpload.class);
	
	
	
	public SystemTestUpload() {
		ContentStatic.loadConfiguration("config.properties");
		
		this.context = new FileSystemXmlApplicationContext(
				"WebContent/WEB-INF/spring/applicationContext.xml");
		
		this.services = context.getBean("RestServices", RestServices.class);
		this.userDao = context.getBean("userDao", DBTecaUserDao.class);
	}
	
	public void uploadProjects(File directoryProjects) {
		try {
			DBTecaUser user = userDao.getUserByUsername("admin");
			
			for(File child : directoryProjects.listFiles()) {
				if(Utility.isCompressed(child.getPath())) {
					JsonUploadReport uploadProcess = (JsonUploadReport) services.
							createUploadProcess(user.getId(), false, "false").getEntity();
					
					logger.info("Upload pacchetto '" + child.getName() + "' in corso ...");
					long t0 = System.currentTimeMillis();
					
					services.uploadFileServer("", uploadProcess.getProcessID(), 
							directoryProjects.getPath(), child.getName());
					
					long t1 = System.currentTimeMillis();
					logger.info("Pacchetto elaborato in " + (t1 - t0) + " ms");
				}
			}
			
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
