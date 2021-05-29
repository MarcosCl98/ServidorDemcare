package com.demcare.demo.service;

import com.demcare.demo.dao.AssociationInstitutionGameDao;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.AssociationInstitutionGame;
import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssociationInstitutionGameService {
    List<Game> findByInstitutionId(Long id);
    List<AssociationInstitutionGame> findByInstitution(User user);
    void deleteById(Long id);
    void suspend(Long id);
    void activate(Long id);
    AssociationInstitutionGame save(AssociationInstitutionGame asociacion);

}
