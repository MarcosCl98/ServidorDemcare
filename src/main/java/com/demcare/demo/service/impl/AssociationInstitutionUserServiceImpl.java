package com.demcare.demo.service.impl;


import com.demcare.demo.dao.AssociationInstitutionUserDao;
import com.demcare.demo.entities.AssociationInstitutionUser;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.AssociationInstitutionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssociationInstitutionUserServiceImpl implements AssociationInstitutionUserService {

    @Autowired
    private AssociationInstitutionUserDao associationInstitutionUserDao;

    @Override
    public AssociationInstitutionUser save(AssociationInstitutionUser associationInstitutionUser) {
        return associationInstitutionUserDao.save(associationInstitutionUser);
    }

    @Override
    public List<AssociationInstitutionUser>  findByUser(User user) {
        Iterable<AssociationInstitutionUser> asociaciones = associationInstitutionUserDao.findAll();
        List<AssociationInstitutionUser> list = new ArrayList<>();

        for(AssociationInstitutionUser a: asociaciones){
            if(a.getUser().getId() == user.getId()){
                list.add(a);
            }
        }
        return list;
    }


}
