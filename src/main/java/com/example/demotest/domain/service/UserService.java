package com.example.demotest.domain.service;

import com.example.demotest.domain.entity.User;

import java.util.List;

public interface UserService {

    int RegisterUser(User user);

    User Login(String userName,String password);

    List<User> getUserByEmail(String email);

    int UpdateUser(User user);
}
