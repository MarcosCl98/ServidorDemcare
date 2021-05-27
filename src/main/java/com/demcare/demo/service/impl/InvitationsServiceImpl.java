package com.demcare.demo.service.impl;

import com.demcare.demo.dao.InvitationsDao;
import com.demcare.demo.dao.SolicitudesDao;
import com.demcare.demo.entities.InvitationsInstitutions;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.InvitationsService;
import com.demcare.demo.service.SolicitudesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationsServiceImpl implements InvitationsService {

    @Autowired
    InvitationsDao invitationsDao;


    @Override
    public void deleteInvitation(Long id) {
        invitationsDao.deleteById(id);
    }

    @Override
    public List<InvitationsInstitutions> findByUser(User user) {
        return invitationsDao.findByUser(user);
    }

    @Override
    public List<InvitationsInstitutions> findByUserInstitution(User user) {
        return invitationsDao.findByUserInstitution(user);
    }
}
