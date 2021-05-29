package com.demcare.demo.service.impl;

import com.demcare.demo.dao.AssociationInstitutionGameDao;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.AssociationInstitutionGame;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.AssociationInstitutionGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssociationInstitutionGameServiceImpl implements AssociationInstitutionGameService {


    @Autowired
    AssociationInstitutionGameDao associationInstitutionGameDao;

    @Override
    public List<Game> findByInstitutionId(Long id) {
        Iterable<AssociationInstitutionGame> games = associationInstitutionGameDao.findAll();
        List<Game> gameList = new ArrayList<>();

        for (AssociationInstitutionGame g: games){
            if(g.getInstitution().getId() == id && !g.getDesactive()){
                gameList.add(g.getGame());
            }
        }
        return gameList;
    }

    @Override
    public List<AssociationInstitutionGame> findByInstitution(User user) {
        return associationInstitutionGameDao.findByInstitution(user);
    }

    @Override
    public void deleteById(Long id) {
        associationInstitutionGameDao.deleteById(id);
    }

    @Override
    public void suspend(Long id) {
        Optional<AssociationInstitutionGame> asociation = associationInstitutionGameDao.findById(id);
        asociation.get().setDesactivate(true);
        associationInstitutionGameDao.save(asociation.get());
    }

    @Override
    public void activate(Long id) {
        Optional<AssociationInstitutionGame> asociation = associationInstitutionGameDao.findById(id);
        asociation.get().setDesactivate(false);
        associationInstitutionGameDao.save(asociation.get());
    }
}
