package com.ibm.bh6.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import com.ibm.bh6.dao.DBUserQuery;
import com.ibm.bh6.model.User;

public class DBUserQueryImpl implements DBUserQuery {

    private static final Logger LOGGER = Logger.getLogger(DBUserQueryImpl.class.getName());

    private UserTransaction utx;
    private EntityManager em;

    public DBUserQueryImpl() {
        utx = getUserTransaction();
        em = getEm();
    }

    @Override
    public List<User> getUsers() {

        LOGGER.info("Get all users");

        TypedQuery<User> typedQuery = em.createQuery("SELECT u FROM User u", User.class);
        List<User> results = typedQuery.getResultList();

        return results;

    }

    @Override
    public User getUser(int userId) {

        LOGGER.info("Get user with id  " + userId);

        TypedQuery<User> typedQuery = em.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class);
        typedQuery.setParameter("userId", userId);
        User result = typedQuery.getSingleResult();

        return result;

    }

    public List<User> getUsersByName(String userName) {
        LOGGER.info("Get all users with name " + userName);

        TypedQuery<User> typedQuery = em.createQuery("SELECT u FROM User u WHERE u.userName = :userName", User.class);
        typedQuery.setParameter("userName", userName);
        List<User> results = typedQuery.getResultList();

        return results;
    }

    private List<User> getStubUsers() {

        List<User> userList = new ArrayList<User>();

        User user = new User();
        user.setUserId(0);
        user.setEmail("frank.adams@renovations.com");
        user.setUserName("Frank Adams");
        user.setJobDesc("Lead Developer");
        user.setPhoneOffice("+49 123 456 78");
        user.setUserImg("fadams.png");

        // TODO Add new users

        userList.add(user);

        return userList;

    }

    private EntityManager getEm() {
        InitialContext ic;
        try {
            ic = new InitialContext();
            return (EntityManager) ic.lookup("java:comp/env/openjpa-todo/entitymanager");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserTransaction getUserTransaction() {
        InitialContext ic;
        try {
            ic = new InitialContext();
            return (UserTransaction) ic.lookup("java:comp/UserTransaction");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
