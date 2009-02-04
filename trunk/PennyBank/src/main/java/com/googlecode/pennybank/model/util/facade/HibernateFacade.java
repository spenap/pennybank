package com.googlecode.pennybank.model.util.facade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateFacade {

	protected static EntityManager entityManager;

	static {
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("pennybank");
		entityManager = entityManagerFactory.createEntityManager();
	}

	public static void closeEntityManager() {
		entityManager.close();
	}

}
