package com.ibm.bh6.dao;

import java.util.List;

import com.ibm.bh6.model.CheckIn;

public interface DBCheckinQuery {

    public List<CheckIn> getCurrentCheckIns();

    public boolean createCheckIn(CheckIn checkIn);

}
