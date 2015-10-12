package com.ibm.bh6.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ibm.bh6.dao.DBHandler;
import com.ibm.bh6.dao.DBLocationQuery;
import com.ibm.bh6.model.Location;

public class DBLocationQueryImpl implements DBLocationQuery {

    private static final Logger LOGGER = Logger.getLogger(DBLocationQueryImpl.class.getName());

    @Override
    public Location getLocation(int locationId) {

        EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get location with id  " + locationId);

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l WHERE l.locationId =:locationId", Location.class);
        typedQuery.setParameter("locationId", locationId);
        Location result = typedQuery.getSingleResult();

        return result;
    }

    @Override
    public List<Location> getLocations() {

        EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get all locations");

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l", Location.class);
        List<Location> results = typedQuery.getResultList();

        return results;
    }

    @Override
    public List<Location> getLocationsByDistance(Float gpsX, Float gpsY) {

        EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get all locations");

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l ORDER BY ABS(m_GPSx - :gpsX + m_GPSy - :gpsY)", Location.class);
        typedQuery.setParameter(":gpsX", gpsX);
        typedQuery.setParameter(":gpsY", gpsY);
        List<Location> results = typedQuery.getResultList();

        return results;
    }

    @Override
    public List<Location> getLocationsByType(String type) {

        EntityManager em = DBHandler.getEntityManager();

        LOGGER.info("Get all locations with type " + type);

        TypedQuery<Location> typedQuery = em.createQuery("SELECT l FROM Location l WHERE l.m_LocType = :type", Location.class);
        typedQuery.setParameter("type", type);
        List<Location> results = typedQuery.getResultList();

        return results;
    }

    @Override
    public List<Location> getLocationsByTypeAndDistance(Float gpsX, Float gpsY, String type) {

        EntityManager em = DBHandler.getEntityManager();

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
        EntityManager em = DBHandler.getEntityManager();

        em.persist(location);

        return true;
    }

}