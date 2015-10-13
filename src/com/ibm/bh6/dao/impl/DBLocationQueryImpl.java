package com.ibm.bh6.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import com.ibm.bh6.dao.DBLocationQuery;
import com.ibm.bh6.model.Location;

public class DBLocationQueryImpl implements DBLocationQuery {

    private static final Logger LOGGER = Logger.getLogger(DBLocationQueryImpl.class.getName());

    private UserTransaction utx;
    private EntityManager em;

    public DBLocationQueryImpl() {
        utx = getUserTransaction();
        em = getEm();
    }

    @Override
    public Location getLocation(int locationId) {

        // EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get location with id  " + locationId);

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l WHERE l.locationId =:locationId", Location.class);
        typedQuery.setParameter("locationId", locationId);
        Location result = typedQuery.getSingleResult();

        return result;
    }

    @Override
    public List<Location> getLocations() {

        // EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get all locations");

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l", Location.class);
        List<Location> results = typedQuery.getResultList();

        return results;
    }

    @Override
    public List<Location> getLocationsByDistance(Float gpsX, Float gpsY) {

        // EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get all locations for: x:"+gpsX+", y:"+gpsY);
        System.out.println("Get all locations for: x:"+gpsX+", y:"+gpsY);

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l ORDER BY ABS(SQRT((l.m_GPSx - :gpsX)*(l.m_GPSx - :gpsX) + (l.m_GPSy - :gpsY)*(l.m_GPSy - :gpsY)))", Location.class);
        typedQuery.setParameter("gpsX", gpsX);
        typedQuery.setParameter("gpsY", gpsY);
        List<Location> results = typedQuery.getResultList();

        return results;
    }

    @Override
    public List<Location> getLocationsByType(String type) {

        // EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get all locations with type " + type);

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l WHERE l.m_LocType = :type", Location.class);
        typedQuery.setParameter("type", type);
        List<Location> results = typedQuery.getResultList();

        return results;
    }

    @Override
    public List<Location> getLocationsByTypeAndDistance(Float gpsX, Float gpsY, String type) {

      // EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get all locations");

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l WHERE l.m_LocType = :type ORDER BY ABS(m_GPSx - :gpsX + m_GPSy - :gpsY)", Location.class);
        typedQuery.setParameter(":gpsX", gpsX);
        typedQuery.setParameter(":gpsY", gpsY);
        typedQuery.setParameter(":type", type);
        List<Location> results = typedQuery.getResultList();

        return results;
    }

    @Override
    public boolean postLocation(Location location) {
        // EntityManager em = DBHandler.getEntityManager();

        try {
            LOGGER.info("persisting location " + location.toString());
            utx.begin();
            em.persist(location);
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