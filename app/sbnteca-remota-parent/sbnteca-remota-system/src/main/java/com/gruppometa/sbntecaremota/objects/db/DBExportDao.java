package com.gruppometa.sbntecaremota.objects.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DBExportDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	

	/**
	 * Inserimento di un export
	 * 
	 * @param export export da inserire
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(DBExport export) throws DaoException {
		try {
			if(export != null)
				entityManager.persist(export);
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}
	
	/**
	 * Modifica di un export
	 * 
	 * @param export export da aggiornare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBExport export) throws DaoException {
		try {
			if(export != null)
				entityManager.merge(export);
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Cancella un export per nome file
	 * 
	 * @param export export da cancellare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void delete(DBExport export) throws DaoException {
		try {
			if(export != null)
				entityManager.remove(entityManager.contains(export) ? export : entityManager.merge(export));
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Preleva tutti gli export completati, ordinati dal più recente al meno recente
	 
	 * @return lista degli export completati
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBExport> getAllCompleteExports() throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT export FROM DBExport export WHERE export.status = 0 ORDER BY export.id DESC");
			return (List<DBExport>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Ricerca l'export per nome file
	 * 
	 * @param filename nome file export
	 * @return DBExport export corrispondente o null se non trovato
	 * @throws DaoException
	 */
	public DBExport getExportByFilename(String filename) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT export FROM DBExport export WHERE export.file = ?1");
			query.setParameter(1, filename);
			return (DBExport) query.getSingleResult();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

}
