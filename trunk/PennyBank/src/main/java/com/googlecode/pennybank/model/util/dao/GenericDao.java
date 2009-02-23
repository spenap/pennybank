package com.googlecode.pennybank.model.util.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;

/**
 * The GenericDAO interface, defining the basic methods to manage
 * the entities' persistence
 *
 * @author spenap
 * @param <E> The entity
 * @param <PK> The primary key type
 */
public interface GenericDao<E, PK extends Serializable> {

    /**
     * Creates a new entity
     *
     * @param entity The entity to be made persistent
     */
    void create(E entity);

    /**
     * Finds an entity using its identifier
     *
     * @param id The identifier to search for
     * @return The entity, if found
     * @throws InstanceNotFoundException if the entity was not found
     */
    E find(PK id)
            throws InstanceNotFoundException;

    /**
     * Checks the existence using a given identifier
     *
     * @param id The identifier for the entity to look for
     * @return A boolean determining if the entity was found or not
     */
    boolean exists(PK id);

    /**
     * Updates an entity
     *
     * @param entity The entity to be updated
     * @return The entity up to date
     */
    E update(E entity);

    /**
     * Deletes an entity given its unique identifier
     *
     * @param id The identifier to look for
     * @throws InstanceNotFoundException if the entity was not found
     */
    void remove(PK id)
            throws InstanceNotFoundException;

    /**
     * Sets the entity manager
     *
     * @param entityManager The entity manager
     */
    void setEntityManager(EntityManager entityManager);
}
