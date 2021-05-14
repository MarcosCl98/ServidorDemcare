package com.demcare.demo.service.impl;

import com.demcare.demo.dao.TokenDao;
import com.demcare.demo.entities.Token;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.RolesService;
import com.demcare.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenDao tokenDao;

    @Override
    public Token save(Token token) {
        return tokenDao.save(token);
    }

    @Override
    public Token findByUser(User user) {
        return tokenDao.findByUser(user);
    }

    @Override
    public Token findByCode(String tokenCode) {
        return tokenDao.findByCode(tokenCode);
    }
}
