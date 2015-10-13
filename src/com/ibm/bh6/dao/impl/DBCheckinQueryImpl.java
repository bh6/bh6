package com.ibm.bh6.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import com.ibm.bh6.dao.DBCheckinQuery;
import com.ibm.bh6.model.CheckIn;

public class DBCheckinQueryImpl implements DBCheckinQuery {

    private static final Logger LOGGER = Logger.getLogger(DBCheckinQueryImpl.class.getName());

    private UserTransaction utx;
    private EntityManager em;

    public DBCheckinQueryImpl() {
        utx = getUserTransaction();
        em = getEm();
    }

    @Override
    public List<CheckIn> getCurrentCheckIns() {
        LOGGER.info("Get current check-ins");

        TypedQuery<CheckIn> typedQuery = em.createQuery("SELECT c FROM CheckIn c WHERE c.m_timestamp ORDER BY c.m_timestamp", CheckIn.class);
        typedQuery.setParameter("currentTime", new Date());
        List<CheckIn> result = typedQuery.getResultList();

        return result;
    }

    public List<CheckIn> getClosestCheckIns(Float gpsX, Float gpsY) {
        LOGGER.info("Get closest check-ins");

        TypedQuery<CheckIn> typedQuery = em.createQuery("SELECT c FROM CheckIn c INNER JOIN Location l ON c.location = l.locationId INNER JOIN User u ON c.user = u.userId "
                + "ORDER BY ABS(SQRT((l.m_GPSx - :gpsX)*(l.m_GPSx - :gpsX) + (l.m_GPSy - :gpsY)*(l.m_GPSy - :gpsY)))", CheckIn.class);
        typedQuery.setParameter("gpsX", gpsX);
        typedQuery.setParameter("gpsY", gpsY);
        List<CheckIn> result = typedQuery.getResultList();

        return null;
    }

    @Override
    public boolean createCheckIn(CheckIn checkIn) {
        try {
            LOGGER.info("persisting checkin " + checkIn.toString());
            utx.begin();
            em.persist(checkIn);
            utx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE) {
                    utx.rollback();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
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
