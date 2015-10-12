package com.ibm.bh6.dao;

import java.util.List;

import javax.xml.stream.Location;

public interface DBLocationQuery {

    /**
     * gets a locations from the db by id
     * 
     * @return {@link Location} location
     */
    public List<String> getLocation();

    /**
     * gets a list of all locations in the db
     * 
     * @return {@link List<Location>} list of locations
     */
    public List<String> getLocations();

    /**
     * gets a list of locations, sorted by the distance to the given Location
     * 
     * @param location
     *            - reference Location to estimate distance from
     * 
     * @return {@link List<Location>} list of locations
     */
    public List<String> getLocationsByDistance();

    /**
     * gets a list of all locations of a specific type
     * 
     * @param type
     *            - location type - Hotel, City or Customer
     * 
     * @return {@link List<Location>} list of locations
     */
    public List<String> getLocationsByType();

    /**
     * gets a list of all locations of a specific type, sorted by the distance
     * to a given location
     * 
     * @param location
     *            - reference Location to estimate the distance from
     * @param type
     *            - location type - Hotel, City or Customer
     * 
     * @return {@link List<Location>} list of locations
     */
    public List<String> getLocationsByTypeAndDistance();

    /**
     * persists a new location
     * 
     * @param location
     *            - location to be persisted
     * @return {@link Boolean} true if success
     */
    public boolean postLocation();

}
