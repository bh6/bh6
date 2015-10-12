package com.ibm.bh6.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.ibm.bh6.dao.DBUserQuery;
import com.ibm.bh6.model.User;

public class DBUserQueryImpl implements DBUserQuery {

    @Override
    public List<User> getUsers() {

        return getStubUsers();

    }

    @Override
    public User getUser(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    private List<User> getStubUsers() {

        List<User> userList = new ArrayList<User>();

        User user = new User();
        user.setEmail("frank.adams@renovations.com");
        user.setUserName("Frank Adams");
        user.setJobDesc("Lead Developer");
        user.setPhoneOffice("+49 123 456 78");
        user.setUserImg("fadams.png");

        // TODO Add new users

        userList.add(user);

        return userList;

    }

}
