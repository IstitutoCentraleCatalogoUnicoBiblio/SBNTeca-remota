package com.gruppometa.sbntecaremota.objects.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DBTecaUserDao {

	@PersistenceContext(unitName="tecaDigitale")
	private EntityManager entityManager;
	

	/**
	 * Inserimento di un utente
	 * 
	 * @param user utente da inserire
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insert(DBTecaUser user) throws DaoException {
		try {
			if(user != null) {
				if(this.getUserByUsername(user.getUsername()) != null)
					throw new DaoException("Utente '" + user.getUsername() + "' già esistente");
				
				entityManager.persist(user);
			}
			
		} catch(Exception e) {
			throw new DaoException("Problema di inserimento nel database", e);
		}
	}
	
	/**
	 * Modifica di un utente
	 * 
	 * @param user utente da aggiornare
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void update(DBTecaUser user) throws DaoException {
		try {
			if(user != null)
				entityManager.merge(user);
			
		} catch(Exception e) {
			throw new DaoException("Problema di aggiornamento nel database", e);
		}
	}

	/**
	 * Preleva utente per username e password
	 
	 * @param username nome utente
	 * @param password password
	 * @return l'utente
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public DBTecaUser getUserByUsername(String username) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT user FROM DBTecaUser user WHERE user.username = ?1 AND user.deleted = 0");
			query.setParameter(1, username);
			List<DBTecaUser> users = (List<DBTecaUser>) query.getResultList();
			return users.isEmpty() ? null : users.get(0);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Preleva utente per ID
	 
	 * @param id ID
	 * @return l'utente
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public DBTecaUser getUserByID(int id) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT user FROM DBTecaUser user WHERE user.id = ?1");
			query.setParameter(1, id);
			List<DBTecaUser> users = (List<DBTecaUser>) query.getResultList();
			return users.isEmpty() ? null : users.get(0);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Preleva utente per email
	 
	 * @param email indirizzo email
	 * @return l'utente
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public DBTecaUser getUserByEmail(String email) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT user FROM DBTecaUser user WHERE user.email = ?1 AND user.deleted = 0");
			query.setParameter(1, email);
			List<DBTecaUser> users = (List<DBTecaUser>) query.getResultList();
			return users.isEmpty() ? null : users.get(0);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Preleva tutti gli utenti
	 
	 * @return lista degli utenti
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBTecaUser> getAllUsers() throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT user FROM DBTecaUser user WHERE user.deleted = 0");
			return (List<DBTecaUser>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Preleva tutti gli utenti cancellati per ruolo di appartenenza
	 * @param role ruolo utente di appartenenza
	 * 
	 * @return lista degli utenti cancellati
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBTecaUser> getAllDeletedUsersByRole(DBUserRole role) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT user FROM DBTecaUser user WHERE user.deleted = 1 AND user.userRole.id = ?1");
			query.setParameter(1, role.getId());
			return (List<DBTecaUser>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

	/**
	 * Preleva tutti gli utenti per ruolo di appartenenza
	 * @param role ruolo utente di appartenenza
	 * 
	 * @return lista degli utenti
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<DBTecaUser> getAllActiveUsersByRole(DBUserRole role) throws DaoException {
		try {
			Query query = entityManager.createQuery("SELECT user FROM DBTecaUser user WHERE user.deleted = 0 AND user.userRole.id = ?1");
			query.setParameter(1, role.getId());
			return (List<DBTecaUser>) query.getResultList();
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}

}
