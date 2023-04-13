package com.gruppometa.sbntecaremota.retrieve;

public class MagPersistenceFactory {

	public static MagPersistence create(String type) {
		if (type.equals(MagPersistenceTypes.FILE)) {
			return new FileMagPersistence();
		} 
		
		else if (type.equals(MagPersistenceTypes.HTTP)) {
			return new HTTPMagPersistence();
		} 
		
		else if (type.equals(MagPersistenceTypes.FTP)) {
			return new FTPMagPersistence();
		} 
		
		else if (type.equals(MagPersistenceTypes.DB)) {
			return new DBMagPersistence();
		}

		return null;
	}

}
