package com.gruppometa.internetculturale.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ComponentFactoryImpl {
	static Configuration configConfiguration = null;
	static SessionFactory configFac=null;
	protected static Configuration createConfigConfiguration() {
//		if(configConfiguration==null)
//			configConfiguration = new Configuration().configure("ConfigDataDao.cfg.xml");
		return configConfiguration;
	}
	public static SessionFactory createConfigSessionFactory(){
		if(configFac==null)
			configFac = createConfigConfiguration().buildSessionFactory();
		return configFac;
	}
}
