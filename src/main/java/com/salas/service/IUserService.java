package com.salas.service;

import com.salas.model.User;

public interface IUserService extends ICRUD<User, Integer> {

    User findOneByUsername(String username);
    void changePassword(String username, String password);

}