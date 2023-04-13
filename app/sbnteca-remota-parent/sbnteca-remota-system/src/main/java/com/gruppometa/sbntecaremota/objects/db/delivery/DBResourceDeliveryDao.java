package com.gruppometa.sbntecaremota.objects.db.delivery;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gruppometa.sbntecaremota.objects.db.DaoException;

/**
 * Servizi per il delivery delle risorse digitali da database
 * 
 * 
 *
 */
public class DBResourceDeliveryDao {

	@PersistenceContext(unitName="tecaDigitaleDelivery")
	private EntityManager entityManager;
	
	

	/**
	 * Ricerca della risorsa digitale per ID
	 * 
	 * @param deliveryID ID del job
	 * @return
	 * @throws DaoException
	 */
	public DBResourceDelivery findByID(String deliveryID) throws DaoException {
		try {
			return entityManager.find(DBResourceDelivery.class, deliveryID);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Inserisce o aggiorna nel delivery
	 * 
	 * @param deliveryIDs lista delle risorse
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void insertOrUpdate(List<DBResourceDelivery> deliveryIDs) throws DaoException {
		try {
			for(DBResourceDelivery dbrd : deliveryIDs)
				entityManager.merge(dbrd);
			
		} catch(Exception e) {
			throw new DaoException("Problema di ricerca nel database", e);
		}
	}
	
	/**
	 * Cancella le risorse dal delivery
	 * 
	 * @param deliveryIDs lista delle risorse
	 * @throws DaoException
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void delete(List<String> deliveryIDs) throws DaoException {
		for(String id : deliveryIDs)
			entityManager.remove(this.findByID(id));
	}

}
