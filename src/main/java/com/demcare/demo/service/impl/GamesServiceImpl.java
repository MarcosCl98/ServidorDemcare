package com.demcare.demo.service.impl;

import com.demcare.demo.dao.AssociationInstitutionUserDao;
import com.demcare.demo.dao.GameDao;
import com.demcare.demo.dao.GamesDao;
import com.demcare.demo.dao.UserDao;
import com.demcare.demo.entities.AssociationInstitutionUser;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.Games;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.GameService;
import com.demcare.demo.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GamesServiceImpl implements GamesService {


    @Autowired
    GamesDao gamesDao;

    @Override
    public List<Game> findByInstitutionId(Long id) {
        Iterable<Games> games = gamesDao.findAll();
        List<Game> gameList = new ArrayList<>();

        for (Games g: games){
            if(g.getInstitution().getId() == id && !g.getDesactive()){
                gameList.add(g.getGame());
            }
        }
        return gameList;
    }
}
