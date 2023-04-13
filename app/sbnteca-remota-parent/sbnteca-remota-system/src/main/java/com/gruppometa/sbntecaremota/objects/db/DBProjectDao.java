package com.gruppometa.sbntecaremota.objects.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servizi database per la tabella contenente le informazioni dei progetti Magteca
 * 
 *
 */
public class DBProjectDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	
	

	/**
	 * Inserimento di un progetto
	 * 
	 * @param project progetto da inserire
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(DBProject project) throws DaoException {
		try {
			if(project != null)
				entityManager.persist(project);
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}

	/**
	 * Modifica di un progetto
	 * 
	 * @param project progetto da aggiornare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBProject project) throws DaoException {
		try {
			if(project != null)
				entityManager.merge(project);
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Modifica di un progetto
	 * 
	 * @param project progetto da cancellare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void delete(DBProject project) throws DaoException {
		try {
			if(project != null)
				entityManager.remove(entityManager.contains(project) ? project : entityManager.merge(project));
			
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
	public DBProject getProjectByName(String name) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT project FROM DBProject project "
					+ "WHERE project.name = ?1 ORDER BY project.lastModified DESC");
			
			query.setParameter(1, name);
			List<DBProject> projects = (List<DBProject>) query.getResultList();
			return projects.isEmpty() ? null : projects.get(0);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * lista dei nuovi progetti (non ancora importati)
	 * 
	 * @return progetto
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<String> getNewProjects() throws DaoException {
		List<String> results = new ArrayList<String>();
		
		try {
			Query query = entityManager.createQuery("SELECT project.name, max(project.lastModified) FROM DBProject project "
					+ "WHERE project.status = 0 GROUP BY project.name");
			
			List<Object[]> projects = query.getResultList();
			
			for(Object[] p : projects)
				results.add((String) p[0]);
			
			return results;
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * lista dei progetti già importati
	 * 
	 * @return progetto
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<String> getImportedProjects() throws DaoException {
		List<String> results = new ArrayList<String>();
		
		try {
			Query query = entityManager.createNativeQuery("SELECT p1.name FROM project p1 INNER JOIN "
					+ "(SELECT name, max(last_modified) AS last_modified FROM project GROUP BY name) p2 "
					+ "ON p1.last_modified = p2.last_modified WHERE status > 0");
			
			List<String> projects = query.getResultList();
			
			for(String p : projects)
				results.add(p);
			
			return results;
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * lista di tutti i progetti
	 * 
	 * @return progetto
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllProjects() throws DaoException {
		List<String> results = new ArrayList<String>();
		
		try {
			Query query = entityManager.createQuery("SELECT project.name, max(project.lastModified) FROM DBProject project "
					+ "GROUP BY project.name");
			
			List<Object[]> projects = query.getResultList();
			
			for(Object[] p : projects)
				results.add((String) p[0]);
			
			return results;
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

}
