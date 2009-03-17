package com.googlecode.pennybank.model.util.facade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * An HibernateFacade
 * 
 * @author spenap
 */
public class HibernateFacade {

    /**
     *  The entity manager
     */
    protected static EntityManager entityManager;

	static {
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("pennybank");
		entityManager = entityManagerFactory.createEntityManager();
	}

    /**
     * Closes the entity manager
     */
    public static void closeEntityManager() {
		entityManager.close();
	}

}
