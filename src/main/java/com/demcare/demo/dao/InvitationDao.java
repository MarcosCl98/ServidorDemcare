package com.demcare.demo.dao;


import com.demcare.demo.entities.Invitation;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationDao extends CrudRepository<Invitation, Long>, JpaSpecificationExecutor<Invitation> {
    List<Invitation> findByUser(User user);
    List<Invitation> findByUserInstitution(User user);
    void deleteById(Long id);

}
