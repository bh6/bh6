package com.ibm.bh6.dao;

import java.util.List;

import com.ibm.bh6.model.Location;

public interface DBLocationQuery {

    /**
     * gets a locations from the db by id
     * 
     * @param id
     *            - id of location in db
     * 
     * @return {@link Location} location
     */
    public Location getLocation(int id);

    /**
     * gets a list of all locations in the db
     * 
     * @return {@link List<Location>} list of locations
     */
    public List<Location> getLocations();

    /**
     * gets a list of locations, sorted by the distance to the given Location
     * 
     * @param location
     *            - reference Location to estimate distance from
     * 
     * @return {@link List<Location>} list of locations
     */
    public List<Location> getLocationsByDistance(Float x, Float y);

    /**
     * gets a list of all locations of a specific type
     * 
     * @param type
     *            - location type - Hotel, City or Customer
     * 
     * @return {@link List<Location>} list of locations
     */
    public List<Location> getLocationsByType(String type);

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
    public List<Location> getLocationsByTypeAndDistance(Float x, Float y, String type);

    /**
     * persists a new location
     * 
     * @param location
     *            - location to be persisted
     * @return {@link Boolean} true if success
     */
    public boolean postLocation(Location location);

}
