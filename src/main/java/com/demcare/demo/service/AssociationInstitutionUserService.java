package com.demcare.demo.service;

import com.demcare.demo.entities.AssociationInstitutionUser;
import org.springframework.stereotype.Service;

@Service
public interface AssociationInstitutionUserService {
    AssociationInstitutionUser save(AssociationInstitutionUser associationInstitutionUser);
}
