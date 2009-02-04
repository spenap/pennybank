package com.googlecode.pennybank.model.util.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;

public interface GenericDao<E, PK extends Serializable> {
	
	void create(E entity);
	
	E find(PK id) throws InstanceNotFoundException;

	boolean exists(PK id);
	
	E update(E entity);
	
	void remove(PK id) throws InstanceNotFoundException;
	
	void setEntityManager(EntityManager entityManager);
}
