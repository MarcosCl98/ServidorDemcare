package com.demcare.demo.dao;


import com.demcare.demo.entities.AsociatedUser;
import com.demcare.demo.entities.InvitationsInstitutions;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationsDao extends CrudRepository<InvitationsInstitutions, Long>, JpaSpecificationExecutor<InvitationsInstitutions> {
    List<InvitationsInstitutions> findAll();

}
