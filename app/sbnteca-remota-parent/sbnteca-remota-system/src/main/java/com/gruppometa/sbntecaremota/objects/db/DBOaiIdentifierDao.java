package com.gruppometa.sbntecaremota.objects.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servizi di lettura e scrittura tabella oai_identifier per il salvataggio di identifier e progressivo
 * utili per la genrazione dell'OAI identifier Solr
 * 
 *
 */
public class DBOaiIdentifierDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	
	

	/**
	 * Aggiornamento di un MAG legato ad un processo di importazione
	 * 
	 * @param importMag il MAG da aggiornare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBOaiIdentifier oaiIdentifier) throws DaoException {
		try {
			if(oaiIdentifier != null)
				entityManager.merge(oaiIdentifier);
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}
	
	/**
	 * Ricerca per ID
	 * 
	 * @param identifier ID
	 * @return record OAI identifier, con idnetifier e progressivo
	 * @throws DaoException
	 */
	public DBOaiIdentifier getOAIIdentifierByID(String identifier) throws DaoException {
		try {
			return entityManager.find(DBOaiIdentifier.class, identifier);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

}
