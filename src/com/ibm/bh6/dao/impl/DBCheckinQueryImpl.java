package com.ibm.bh6.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import com.ibm.bh6.dao.DBCheckinQuery;
import com.ibm.bh6.dao.DBHandler;
import com.ibm.bh6.model.CheckIn;

public class DBCheckinQueryImpl implements DBCheckinQuery {

    private static final Logger LOGGER = Logger.getLogger(DBCheckinQueryImpl.class.getName());

    private UserTransaction utx;
    private EntityManager em;

    public DBCheckinQueryImpl() {
        utx = DBHandler.getUserTransaction();
        em = DBHandler.getEntityManager();
    }

    @Override
    public List<CheckIn> getCurrentCheckIns() {
        LOGGER.info("Get current check-ins");

        TypedQuery<CheckIn> typedQuery = em.createQuery("SELECT c FROM CheckIn c WHERE c.m_timestamp > :currentTime - 60000", CheckIn.class);
        typedQuery.setParameter("currentTime", new Date());
        List<CheckIn> result = typedQuery.getResultList();

        return result;
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

}
