package com.ibm.bh6.dao;

import java.util.List;

import com.ibm.bh6.model.CheckIn;

public interface DBCheckinQuery {

    public List<CheckIn> getCurrentCheckIns();

    public List<CheckIn> getClosestCheckIns(Float gpsX, Float gpsY);

    public boolean createCheckIn(CheckIn checkIn);

}
