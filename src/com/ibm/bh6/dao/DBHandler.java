package com.ibm.bh6.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBHandler {

    private static final String PERSISTENCE_UNIT_NAME = "bh6db";
    private static EntityManagerFactory factory;

    protected static EntityManagerFactory DBConnection() throws Exception {

        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            return factory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static EntityManagerFactory getEntityManagerFactory() {

        if (factory == null) {
            try {
                factory = DBConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return factory;

    }

    public static EntityManager getEntityManager() {

        EntityManagerFactory emf = getEntityManagerFactory();
        return emf.createEntityManager();

    }

}
