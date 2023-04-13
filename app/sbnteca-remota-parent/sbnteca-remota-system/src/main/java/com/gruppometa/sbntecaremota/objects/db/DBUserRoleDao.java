package com.gruppometa.sbntecaremota.objects.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DBUserRoleDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;

	
	
	/**
	 * Inserimento di un ruolo
	 * 
	 * @param role ruolo utente da inserire
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(DBUserRole role) throws DaoException {
		try {
			if(role != null)
				entityManager.persist(role);
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}

	/**
	 * Aggiornamento di un ruolo
	 * 
	 * @param role ruolo utente da aggiornare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBUserRole role) throws DaoException {
		try {
			if(role != null)
				entityManager.merge(role);
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}

	/**
	 * Cancellazione di un ruolo
	 * 
	 * @param role ruolo utente da cancellare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void delete(DBUserRole role) throws DaoException {
		try {
			if(role != null)
				entityManager.remove(entityManager.contains(role) ? role : entityManager.merge(role));
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}
	
	/**
	 * Prende tutti i ruoli
	 * 
	 * @return lista dei ruoli
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBUserRole> getAllRoles() throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT role FROM DBUserRole role");
			return (List<DBUserRole>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Ruolo per ID
	 * 
	 * @return DBUserRole ruolo
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public DBUserRole getRoleByID(int id) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT role FROM DBUserRole role WHERE role.id = ?1");
			query.setParameter(1, id);
			List<DBUserRole> roles = (List<DBUserRole>) query.getResultList();
			return roles.isEmpty() ? null : roles.get(0);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Ruolo per nome
	 * 
	 * @return DBUserRole ruolo
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public DBUserRole getRoleByName(String name) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT role FROM DBUserRole role WHERE role.name = ?1");
			query.setParameter(1, name);
			List<DBUserRole> roles = (List<DBUserRole>) query.getResultList();
			return roles.isEmpty() ? null : roles.get(0);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

}
