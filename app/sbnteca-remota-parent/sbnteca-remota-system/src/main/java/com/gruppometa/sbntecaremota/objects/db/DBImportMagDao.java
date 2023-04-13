package com.gruppometa.sbntecaremota.objects.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servizi database per la tabella contenente le informazioni dei MAG relativi ad un processo di importazione
 * 
 *
 */
public class DBImportMagDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	
	
	/**
	 * Cerca i MAG da importare per un determinato job
	 * 
	 * @param jobID ID del job
	 * @return la lista dei MAG legati al job
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBImportMag> getMagsByJob(String jobID) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT import_mag FROM DBImportMag import_mag WHERE import_mag.tecaProcess.id = :job");
			query.setParameter("job", jobID);
			return (List<DBImportMag>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Ricerca tutti i MAG di un job che sono stati già elaborati
	 * 
	 * @param jobID ID del job
	 * @return la lista dei MAG legati al job già elaborati
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBImportMag> getMagsByJobResult(String jobID) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT import_mag FROM DBImportMag import_mag "
					+ "WHERE import_mag.tecaProcess.id = :job AND import_mag.result IS NOT NULL");
			
			query.setParameter("job", jobID);
			return (List<DBImportMag>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Preleva tutti i MAG di un job per i quali si sono verificati WARNING
	 * 
	 * @param jobID ID del job
	 * @return la lista dei MAG legati al job con segnalazioni di WARNING
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBImportMag> getMagsByJobWarnings(String jobID) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT import_mag FROM DBImportMag import_mag "
					+ "WHERE import_mag.tecaProcess.id = :job AND import_mag.warnings IS NOT NULL AND import_mag.result IS NOT NULL");
			
			query.setParameter("job", jobID);
			return (List<DBImportMag>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Preleva i MAG relativi ad un job per i quali si sono verificati ERROR
	 * 
	 * @param jobID ID del job
	 * @returnla lista dei MAG legati al job con segnalazioni di ERROR
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBImportMag> getMagsByJobErrors(String jobID) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT import_mag FROM DBImportMag import_mag "
					+ "WHERE import_mag.tecaProcess.id = :job AND (import_mag.errors IS NOT NULL OR import_mag.fatalErrors IS NOT NULL) "
					+ "AND import_mag.result IS NOT NULL");
			
			
			query.setParameter("job", jobID);
			return (List<DBImportMag>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Inserimento dei MAG di un proceso di importazione
	 * 
	 * @param mags i MAG da importare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(List<DBImportMag> mags) throws DaoException {
		try {
			if(mags != null) {
				for(DBImportMag mag : mags)
					entityManager.persist(mag);
			}
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}

	/**
	 * Aggiornamento di un MAG legato ad un processo di importazione
	 * 
	 * @param importMag il MAG da aggiornare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBImportMag importMag) throws DaoException {
		try {
			if(importMag != null)
				entityManager.merge(importMag);
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Elimina i MAG importati rispetto ad un progetto cancellato
	 * 
	 * @param project progetto rispetto al quale eseguire le cancellazioni
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void deleteByProject(DBProject project) throws DaoException {
		try {
			if(project != null) {
				Query query = entityManager.createQuery("DELETE FROM DBImportMag importMag WHERE importMag.project.id=?1");
				query.setParameter(1, project.getId());
				query.executeUpdate();
			}
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Restituisce il MAG contrassegnato dal job e dal path sul file system
	 * 
	 * @param jobID ID del job
	 * @param path percorso sul file system
	 * @return Il MAG identificato dal job e dal path
	 * @throws DaoException
	 */
	public DBImportMag getMagByJobPath(String jobID, String path) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT import_mag FROM DBImportMag import_mag "
					+ "WHERE import_mag.tecaProcess.id = :job AND import_mag.mag = :path");
			
			query.setParameter("job", jobID);
			query.setParameter("path", path);
			return (DBImportMag) query.getSingleResult();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Restituisce l'ultimo job relativo all'ultima importazione effettivamente eseguita
	 * 
	 * @param path path assoluto del MAG
	 * @return ID del job
	 * @throws DaoException
	 */
	public String getLastCompleteImportJobByMag(String path) throws DaoException {
		try {
			Query query = entityManager.createNativeQuery(""
					+ "SELECT import_mag.mag, import_mag.id_job, MAX(import_mag.validation_time) AS time "
					+ "FROM import_mag INNER JOIN import_detail ON import_mag.id_job = import_detail.id_job "
					+ "WHERE import_detail.dress_flag >=0 AND import_detail.public_flag >=0 AND import_mag.mag=? "
					+ "GROUP BY import_mag.mag, import_mag.id_job ORDER BY time DESC");
			
			query.setParameter(1, path);
			List<Object[]> results = (List<Object[]>) query.getResultList();
			
			if(results.isEmpty())
				return null;
			
			return results.get(0)[1].toString();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

}
