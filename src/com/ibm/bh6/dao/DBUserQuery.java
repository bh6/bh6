package com.ibm.bh6.dao;

import java.util.List;

import com.ibm.bh6.model.User;

public interface DBUserQuery {

    public List<User> getUsers();

    public User getUser(int id);

    public User getUser(String name);

}
