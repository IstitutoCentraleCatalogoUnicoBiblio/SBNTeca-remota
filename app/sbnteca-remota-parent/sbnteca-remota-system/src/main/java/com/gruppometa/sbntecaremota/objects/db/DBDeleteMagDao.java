package com.gruppometa.sbntecaremota.objects.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servizi database per la tabella contenente le informazioni dei MAG legati ad un processo di cancellazione
 * 
 *
 */
public class DBDeleteMagDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;

	
	/**
	 * Inserimento MAG cancellati sul database
	 * 
	 * @param mags i mags cancellati
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(List<DBDeleteMag> mags) throws DaoException {
		try {
			if(mags != null) {
				for(DBDeleteMag mag : mags)
					entityManager.persist(mag);
			}
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}

}
