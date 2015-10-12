package com.ibm.bh6.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

public class DBHandler {

    private static final String PERSISTENCE_UNIT_NAME = "openjpa-todo";
    private static EntityManagerFactory factory;
    private static UserTransaction userTransaction;

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

    public static UserTransaction getUserTransaction() {

        if (userTransaction == null) {
            InitialContext ic;
            try {
                ic = new InitialContext();
                return (UserTransaction) ic.lookup("java:comp/UserTransaction");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return null;
        }
        return userTransaction;
    }

}
