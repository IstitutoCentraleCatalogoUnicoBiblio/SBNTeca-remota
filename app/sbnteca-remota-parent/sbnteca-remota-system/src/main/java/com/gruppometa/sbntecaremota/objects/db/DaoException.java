package com.gruppometa.sbntecaremota.objects.db;

/**
 * Eccezione per accesso al database
 * 
 *
 */
public class DaoException extends Exception {
	private static final long serialVersionUID = 7015374235952462203L;
	
	public DaoException() {
		super();
	}
	
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(Throwable throwable) {
		super(throwable);
	}
	
	public DaoException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
