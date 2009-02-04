package com.googlecode.pennybank.model.util.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;

public class GenericDAOHibernate<E, PK extends Serializable> implements
		GenericDao<E, PK> {

	private EntityManager entityManager;
	private Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDAOHibernate() {
		this.entityClass = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void create(E entity) {
		getSession().persist(entity);

	}

	public boolean exists(PK id) {
		return getSession().createCriteria(entityClass).add(
				Restrictions.idEq(id)).setProjection(Projections.id())
				.uniqueResult() != null;
	}

	@SuppressWarnings("unchecked")
	public E find(PK id) throws InstanceNotFoundException {
		E entity = (E) getSession().get(entityClass, id);

		if (entity == null) {
			throw new InstanceNotFoundException(id, entityClass.getName());
		}

		return entity;
	}

	/**
	 * @return the sessionFactory
	 */
	public Session getSession() {
		// return sessionFactory.getCurrentSession();
		return (Session) entityManager.getDelegate();
	}

	public void remove(PK id) throws InstanceNotFoundException {
		getSession().delete(find(id));

	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	public E update(E entity) {
		return (E) getSession().merge(entity);
	}

}
