package com.demcare.demo.service.impl;

import com.demcare.demo.dao.GameDao;
import com.demcare.demo.dao.TokenDao;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.Token;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.GameService;
import com.demcare.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameDao gameDao;

    @Override
    public Game save(Game game) {
        return gameDao.save(game);
    }

    @Override
    public Game findByTitulo(String titulo) {
        return gameDao.findByTitulo(titulo);
    }

    @Override
    public List<Game> findAll() {
        Iterable<Game> iterable = gameDao.findAll();
        List <Game> list = new ArrayList<Game>();
        for (Game item : iterable) {
            list.add( item);

        }
        return list;
    }


}
