package com.demcare.demo.dao;


import com.demcare.demo.entities.SolicitudesInstitutions;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudesDao extends CrudRepository<SolicitudesInstitutions, Long>, JpaSpecificationExecutor<SolicitudesInstitutions> {
    List<SolicitudesInstitutions> findByUser(User user);
    List<SolicitudesInstitutions> findByUserInstitution(User user);
    void deleteById(Long id);
}
