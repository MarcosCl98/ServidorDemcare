package com.demcare.demo.service;

import com.demcare.demo.model.UserModel;

public interface UserService {
    UserModel findByMail(String mail);
    void deleteByMail(String mail);
    UserModel register(UserModel userEntity);
    UserModel update(UserModel userEntity);
}
