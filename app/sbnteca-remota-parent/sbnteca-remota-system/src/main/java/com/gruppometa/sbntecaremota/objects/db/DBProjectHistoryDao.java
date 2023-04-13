package com.gruppometa.sbntecaremota.objects.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DBProjectHistoryDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	
	

	/**
	 * Inserimento di una cronologia progetto/processo
	 * 
	 * @param project progetto da inserire
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(DBProjectHistory projectHistory) throws DaoException {
		try {
			if(projectHistory != null)
				entityManager.persist(projectHistory);
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}

	/**
	 * Modifica di una cronologia progetto/processo
	 * 
	 * @param project progetto da aggiornare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBProjectHistory projectHistory) throws DaoException {
		try {
			if(projectHistory != null)
				entityManager.merge(projectHistory);
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Elimina la cronologia rispetto ad un progetto cancellato
	 * 
	 * @param project progetto rispetto al quale eseguire le cancellazioni
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void deleteByProject(DBProject project) throws DaoException {
		try {
			if(project != null) {
				Query query = entityManager.createQuery("DELETE FROM DBProjectHistory history WHERE history.project.id=?1");
				query.setParameter(1, project.getId());
				query.executeUpdate();
			}
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Progetto per nome
	 * 
	 * @return progetto
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBProjectHistory> getHistoryByProjectName(String name) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT history FROM DBProjectHistory history "
					+ "WHERE history.project.name = ?1 ORDER BY history.timestampOperation DESC");
			
			query.setParameter(1, name);
			return (List<DBProjectHistory>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Cronologia per nome progetto e job di appartenenza
	 * 
	 * @return progetto
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public DBProjectHistory getProjectHistoryByNameAndJob(String name, String jobID) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT history FROM DBProjectHistory history "
					+ "WHERE history.project.name = ?1 AND history.tecaProcess.id = ?2");
			
			query.setParameter(1, name);
			query.setParameter(2, jobID);
			List<DBProjectHistory> results = (List<DBProjectHistory>) query.getResultList();
			return results.isEmpty() ? null : results.get(0);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Cronologia per nome progetto e job di appartenenza
	 * 
	 * @return progetto
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBProjectHistory> getProjectsHistoryByJob(String jobID) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT history FROM DBProjectHistory history "
					+ "WHERE history.tecaProcess.id = ?1");
			
			query.setParameter(1, jobID);
			List<DBProjectHistory> results = (List<DBProjectHistory>) query.getResultList();
			return results == null ? new ArrayList<DBProjectHistory>() : results;
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

}
