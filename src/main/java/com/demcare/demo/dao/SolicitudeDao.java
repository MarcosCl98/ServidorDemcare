package com.demcare.demo.dao;


import com.demcare.demo.entities.Solicitude;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudeDao extends CrudRepository<Solicitude, Long>, JpaSpecificationExecutor<Solicitude> {
    List<Solicitude> findByUser(User user);
    List<Solicitude> findByUserInstitution(User user);
    void deleteById(Long id);
}
