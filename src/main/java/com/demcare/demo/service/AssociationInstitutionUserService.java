package com.demcare.demo.service;

import com.demcare.demo.entities.AssociationInstitutionUser;
import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssociationInstitutionUserService {
    AssociationInstitutionUser save(AssociationInstitutionUser associationInstitutionUser);
    List<AssociationInstitutionUser> findByUser(User user);
    List<AssociationInstitutionUser> findByUserInstitution(User user);
    void deleteAssociationInstitutionUser(Long id);
}
