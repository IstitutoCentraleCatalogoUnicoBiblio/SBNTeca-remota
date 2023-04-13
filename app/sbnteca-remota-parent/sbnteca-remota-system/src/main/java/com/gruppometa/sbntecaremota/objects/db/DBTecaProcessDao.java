package com.gruppometa.sbntecaremota.objects.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servizi database per la tabella contenente le informazioni di base di un processo
 * 
 *
 */
public class DBTecaProcessDao {
	
	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	
	
	/**
	 * Ricerca del processo identificato dall'ID
	 * 
	 * @param processID ID del job
	 * @return
	 * @throws DaoException
	 */
	public DBTecaProcess findByID(String processID) throws DaoException {
		try {
			return entityManager.find(DBTecaProcess.class, processID);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Preleva tutti i job
	 * 
	 * @return la lista di tutti i processi
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBTecaProcess> getAll() throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT tp FROM DBTecaProcess tp");
			return (List<DBTecaProcess>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Preleva tutte le bozze
	 * 
	 * @return la lista di tutte le bozze
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBTecaProcess> getDrafts() throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT tp FROM DBTecaProcess tp WHERE tp.id LIKE 'draft_%'");
			return (List<DBTecaProcess>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Inserimento di un processo
	 * 
	 * @param tecaProcess processo da inserire
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(DBTecaProcess tecaProcess) throws DaoException {
		try {
			if(tecaProcess != null)
				entityManager.persist(tecaProcess);
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}
	
	/**
	 * Modifica di un processo
	 * 
	 * @param tecaProcess processo da aggiornare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBTecaProcess tecaProcess) throws DaoException {
		try {
			if(tecaProcess != null)
				entityManager.merge(tecaProcess);
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Cancellazione di una bozza
	 * 
	 * @param draft bozza da cancellare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void deleteDraft(DBTecaProcess draft) throws DaoException {
		try {
			if(draft != null && draft.getId().startsWith("draft_"))
				entityManager.remove(entityManager.contains(draft) ? draft : entityManager.merge(draft));
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}
}
