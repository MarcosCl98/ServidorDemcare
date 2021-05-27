package com.demcare.demo.dao;


import com.demcare.demo.entities.InvitationsInstitutions;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationsDao extends CrudRepository<InvitationsInstitutions, Long>, JpaSpecificationExecutor<InvitationsInstitutions> {
    List<InvitationsInstitutions> findByUser(User user);
    List<InvitationsInstitutions> findByUserInstitution(User user);
    void deleteById(Long id);

}
