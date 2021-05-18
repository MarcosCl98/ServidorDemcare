package com.demcare.demo.service.impl;


import com.demcare.demo.dao.AssociationInstitutionUserDao;
import com.demcare.demo.entities.AssociationInstitutionUser;
import com.demcare.demo.service.AssociationInstitutionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociationInstitutionUserServiceImpl implements AssociationInstitutionUserService {

    @Autowired
    private AssociationInstitutionUserDao associationInstitutionUserDao;

    @Override
    public AssociationInstitutionUser save(AssociationInstitutionUser associationInstitutionUser) {
        return associationInstitutionUserDao.save(associationInstitutionUser);
    }


}
