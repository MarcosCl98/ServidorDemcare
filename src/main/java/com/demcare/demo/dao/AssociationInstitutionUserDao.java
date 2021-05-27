package com.demcare.demo.dao;


import com.demcare.demo.entities.AssociationInstitutionUser;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociationInstitutionUserDao extends CrudRepository<AssociationInstitutionUser, Long>, JpaSpecificationExecutor<AssociationInstitutionUser> {
    List<AssociationInstitutionUser> findByUser(User user);

}
