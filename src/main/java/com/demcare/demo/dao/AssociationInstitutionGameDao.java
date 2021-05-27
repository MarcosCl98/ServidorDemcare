package com.demcare.demo.dao;


import com.demcare.demo.entities.AssociationInstitutionGame;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociationInstitutionGameDao extends CrudRepository<AssociationInstitutionGame, Long>, JpaSpecificationExecutor<AssociationInstitutionGame> {
    List<AssociationInstitutionGame> findByInstitution(User user);
    void deleteById(Long id);
}
