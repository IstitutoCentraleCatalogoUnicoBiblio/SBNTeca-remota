package com.gruppometa.sbntecaremota.objects.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servizi database per la tabella contenente le informazioni di base di un processo di importazione
 * 
 *
 */
public class DBImportDetailDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	
	
	/**
	 * Preleva le informazioni generali di un servizio di importazione, a partire dal job
	 * 
	 * @param jobID ID del job
	 * @return informazioni generali processo di importazione
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public DBImportDetail getImportDetailByJob(String jobID) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT detail FROM DBImportDetail detail WHERE detail.tecaProcess.id = :job");
			query.setParameter("job", jobID);
			
			List<DBImportDetail> details = (List<DBImportDetail>) query.getResultList();
			return details.isEmpty() ? null : details.get(0);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Inserimento dei dati di informazioni generali di un processo di importazione
	 * 
	 * @param detail dettaglio delle informazioni generali
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(DBImportDetail detail) throws DaoException {
		try {
			if(detail != null)
				entityManager.persist(detail);
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}
	
	/**
	 * Aggiornamento delle informazioni generali di un processo di importazione
	 * 
	 * @param detail dettaglio delle informazioni generali
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBImportDetail detail) throws DaoException {
		try {
			if(detail != null)
				entityManager.merge(detail);
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

}
