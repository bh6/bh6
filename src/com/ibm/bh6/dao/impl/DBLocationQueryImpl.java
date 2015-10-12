package com.ibm.bh6.dao.impl;

import java.util.List;

import com.ibm.bh6.dao.DBLocationQuery;
import com.ibm.bh6.model.Location;

public class DBLocationQueryImpl implements DBLocationQuery {

    @Override
    public List<Location> getLocation(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Location> getLocations() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Location> getLocationsByDistance(Float x, Float y) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Location> getLocationsByType(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Location> getLocationsByTypeAndDistance(Float x, Float y, String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean postLocation(Location location) {
        // TODO Auto-generated method stub
        return false;
    }

}