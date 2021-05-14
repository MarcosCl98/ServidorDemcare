package com.demcare.demo.service;

import com.demcare.demo.entities.Token;
import com.demcare.demo.entities.User;

public interface TokenService {
     Token save(Token token);
     Token findByUser(User user);
     Token findByCode(String tokenCode);
}
