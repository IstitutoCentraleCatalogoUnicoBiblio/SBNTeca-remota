package com.gruppometa.sbntecaremota.objects.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servizi database per la tabella contenente le informazioni dei MAG legati ad un processo di pubblicazione
 * 
 * NON USATA!!!
 * 
 *
 */
public class DBPublicationMagDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	
	
	/**
	 * Inserimento dei MAG relativi ad un processo di pubblicazione
	 * 
	 * @param mags i MAG da elaborati per una pubblicazione
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(List<DBPublicationMag> mags) throws DaoException {
		try {
			if(mags != null) {
				for(DBPublicationMag mag : mags)
					entityManager.persist(mag);
			}
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}

}
