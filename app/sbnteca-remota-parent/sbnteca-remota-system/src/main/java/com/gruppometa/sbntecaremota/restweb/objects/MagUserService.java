package com.gruppometa.sbntecaremota.restweb.objects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gruppometa.sbntecaremota.objects.db.DBTecaUser;
import com.gruppometa.sbntecaremota.objects.db.DBTecaUserDao;
import com.gruppometa.sbntecaremota.objects.db.DBUserRole;
import com.gruppometa.sbntecaremota.objects.db.DBUserRoleDao;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.objects.json.JsonPostUser;

/**
 * ALL'UTENTE È ASSOCIATO UN RUOLO
 * UN RUOLO HA UN NOME ED È COSTITUITO DA UN INSIEME DI PERMESSI PER LA FRUIZIONE DELLE FUNZIONALITÀ DELLA TECA
 * 
 * @author marco
 *
 */
public class MagUserService {
	
	// dao utenti
	@Autowired
	private DBTecaUserDao userDao;

	// dao ruoli
	@Autowired
	private DBUserRoleDao roleDao;
	
	
	/**
	 * Esegue la cancellazione dell'ID specificato
	 * 
	 * CANCELLAZIONE UTENTE
	 * 
	 * @param userID
	 * @return
	 */
	public void deleteUser(int userID) throws DaoException {
		DBTecaUser user = userDao.getUserByID(userID);
		
		if(user == null)
			throw new DaoException("Operazione non eseguita, utente ID " + userID + " non trovato");
		
		user.setDeleted(true);
		userDao.update(user);
	}
	
	/**
	 * Esegue l'inserimento di un utente
	 * 
	 * INSERIMENTO UTENTE
	 * 
	 * @param requestUser oggetto contenente i metadati utente come passati alla richiesta
	 * @throws DaoException
	 */
	public void insertUser(JsonPostUser requestUser) throws DaoException {
		DBTecaUser user = new DBTecaUser();
		user.setName(requestUser.getName());
		user.setSurname(requestUser.getSurname());
		user.setEmail(requestUser.getEmail());
		user.setUsername(requestUser.getUsername());
		user.setPassword(requestUser.getPassword());
		user.setEnabled(requestUser.getEnabled());
		
		DBUserRole role = roleDao.getRoleByID(requestUser.getRoleID());
		
		if(role == null)
			throw new DaoException("Ruolo utente non riconosciuto");
		
		user.setUserRole(role);
		userDao.insert(user);
	}

	/**
	 * Esegue l'aggiornamento di un utente
	 * 
	 * AGGIORNAMENTO UTENTE
	 * 
	 * @param requestUser oggetto contenente i metadati utente come passati alla richiesta
	 * @throws DaoException
	 */
	public void updateUser(JsonPostUser requestUser) throws DaoException {
		DBTecaUser user = new DBTecaUser();
		user.setId(requestUser.getId());
		user.setName(requestUser.getName());
		user.setSurname(requestUser.getSurname());
		user.setEmail(requestUser.getEmail());
		user.setUsername(requestUser.getUsername());
		user.setPassword(requestUser.getPassword());
		user.setEnabled(requestUser.getEnabled());
		
		DBUserRole role = roleDao.getRoleByID(requestUser.getRoleID());
		
		if(role == null)
			throw new DaoException("Ruolo utente non riconosciuto");

		user.setUserRole(role);
		userDao.update(user);
	}
	
	/**
	 * Esegue la ricerca per nome utente
	 * 
	 * RICERCA UTENTE PER USERNAME
	 * 
	 * @param username username
	 * @return DBTecaUser oggetto metadati utente come memorizzati sul database
	 * @throws DaoException 
	 */
	public DBTecaUser searchUserByUsername(String username) throws DaoException {
		return userDao.getUserByUsername(username);
	}

	/**
	 * Esegue la ricerca per email
	 * 
	 * RICERCA UTENTE PER EMAIL (NON USATO!)
	 * 
	 * @param email email
	 * @return DBTecaUser oggetto metadati utente come memorizzati sul database
	 * @throws DaoException 
	 */
	public DBTecaUser searchUserByEmail(String email) throws DaoException {
		return userDao.getUserByEmail(email);
	}

	/**
	 * Esegue la ricerca per ID utente
	 * 
	 * RICERCA UTENTE PER ID
	 * 
	 * @param id ID utente
	 * @return DBTecaUser oggetto metadati utente come memorizzati sul database
	 * @throws DaoException 
	 */
	public DBTecaUser searchUserByID(int id) throws DaoException {
		return userDao.getUserByID(id);
	}
	
	/**
	 * Ricerca tutti gli utenti
	 * 
	 * LETTURA UTENTI
	 *  
	 * @return List<DBTecaUser> lista degli utenti
	 * @throws DaoException 
	 */
	public List<DBTecaUser> getAllUsers() throws DaoException {
		return userDao.getAllUsers();
	}
	
	/**
	 * Esegue l'inserimento del ruolo utente
	 * 
	 * INSERIMENTO RUOLO
	 * 
	 * @param role ruolo utente
	 * @throws DaoException
	 */
	public void insertRole(DBUserRole role) throws DaoException {
		if(roleDao.getRoleByName(role.getName()) != null)
			throw new DaoException("Ruolo '" + role.getName() + "' già esistente");
		
		roleDao.insert(role);
	}
	
	/**
	 * Esegue l'aggiornamento del ruolo utente
	 * 
	 * AGGIORNAMENTO RUOLO
	 * 
	 * @param role ruolo utente
	 * @throws DaoException
	 */
	public void updateRole(DBUserRole role) throws DaoException {
		roleDao.update(role);
	}

	/**
	 * Esegue la cancellazione del ruolo utente
	 * 
	 * CANCELLAZIONE RUOLO
	 * 
	 * @param roleID ID ruolo
	 * @throws DaoException
	 */
	public void deleteRole(int roleID) throws DaoException {
		DBUserRole role = roleDao.getRoleByID(roleID);
		
		if(role == null)
			throw new DaoException("ID ruolo " + roleID + " non esistente");
		
		List<DBTecaUser> deletedUsers = userDao.getAllDeletedUsersByRole(role);
		
		for(DBTecaUser deletedUser : deletedUsers) {
			deletedUser.setUserRole(null);
			userDao.update(deletedUser);
		}
		
		List<DBTecaUser> roleUsers = userDao.getAllActiveUsersByRole(role);
		
		if(roleUsers.isEmpty())
			roleDao.delete(role);
		
		else throw new DaoException("Ruolo non eliminabile, almeno un utente possiede il ruolo '" + role.getName() + "'");
	}

	/**
	 * Ricerca tutti i ruoli utente
	 * 
	 * LETTURA RUOLI
	 *  
	 * @return List<DBUserRole> lista dei ruoli utente
	 * @throws DaoException 
	 */
	public List<DBUserRole> getAllRoles() throws DaoException {
		return roleDao.getAllRoles();
	}
 
}
