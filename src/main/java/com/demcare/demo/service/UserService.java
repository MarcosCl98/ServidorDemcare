package com.demcare.demo.service;

import com.demcare.demo.entities.User;

import java.util.List;

public interface UserService {
    User findByMail(String mail);
    void deleteUser(Long id);
    void suspendUser(Long id);
    void activateUser(Long id);
    User register(User userEntity);
    List<User> getUsers();
    List<User> getUsersList();
}
