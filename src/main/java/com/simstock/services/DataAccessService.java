package com.simstock.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Generic Data Access Service
 * 
 * The basic data access operations for any persistent object are performed in
 * this class.
 **/
public abstract class DataAccessService<T> {

	@PersistenceContext
	private EntityManager em;

	public DataAccessService() {
	}

	private Class<T> type;

	/**
	 * Default constructor
	 *
	 * @param type
	 *            entity class
	 */
	public DataAccessService(Class<T> type) {
		this.type = type;
	}

	/**
	 * Stores an instance of the entity class in the database
	 * 
	 * @param T
	 *            Object
	 * @return
	 */
	public T create(T t) {
		this.em.persist(t);
		this.em.flush();
		this.em.refresh(t);
		return t;
	}

	/**
	 * Retrieves an entity instance that was previously persisted to the
	 * database
	 * 
	 * @param T
	 *            Object
	 * @param id
	 * @return
	 */
	public T find(Object id) {
		return this.em.find(this.type, id);
	}

}