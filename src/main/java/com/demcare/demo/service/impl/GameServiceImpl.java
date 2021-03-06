package com.demcare.demo.service.impl;

import com.demcare.demo.dao.*;
import com.demcare.demo.entities.*;
import com.demcare.demo.service.GameService;
import com.demcare.demo.service.AssociationInstitutionGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameDao gameDao;

    @Autowired
    AssociationInstitutionGameDao associationInstitutionGameDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AssociationInstitutionUserDao associationInstitutionUserDao;

    @Override
    public Game save(Game game) {
        return gameDao.save(game);
    }

    @Override
    public void activate(Long id) {
        Optional<Game> game = gameDao.findById(id);
        //game.get().setDesactive(false);
        gameDao.save(game.get());
    }

    @Override
    public void desactivate(Long id) {
        Optional<Game> game= gameDao.findById(id);
        //game.get().setDesactive(true);
        gameDao.save(game.get());
    }

    @Override
    public Game findByTitulo(String titulo) {
        return gameDao.findByTitulo(titulo);
    }

    @Override
    public Game findById(Long id) {
        return gameDao.findById(id).get();
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

    @Override
    public List<Game> findActiveGames(Long idJugador) {
        List<AssociationInstitutionUser> asociaciones = associationInstitutionUserDao.findByUser(userDao.findById(idJugador).get());
        List <Game> list = new ArrayList<Game>();
        if(asociaciones.size() > 0){
            AssociationInstitutionUser asociacion = asociaciones.get(0);
            User institucion = asociacion.getUserInstitution();
            List<AssociationInstitutionGame> asociacionesJuego = associationInstitutionGameDao.findByInstitution(institucion);
            for(AssociationInstitutionGame a: asociacionesJuego){
                if(!a.getDesactive()){
                    list.add(a.getGame());
                }
            }
        }else{
            Iterable <Game> iterable  = gameDao.findAll();
            for(Game item: iterable){
                list.add(item);
            }
        }
        return list;
    }


}
