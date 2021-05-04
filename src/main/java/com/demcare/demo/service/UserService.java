package com.demcare.demo.service;

import com.demcare.demo.entities.User;

import java.util.List;

public interface UserService {
    User findByMail(String mail);
    void deleteByMail(String mail);
    User register(User userEntity);
    User update(User userEntity);
    List<User> getUsers();
}
