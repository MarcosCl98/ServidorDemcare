package com.demcare.demo.service.impl;

import com.demcare.demo.dao.InvitationDao;
import com.demcare.demo.entities.Invitation;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    InvitationDao invitationDao;

    @Override
    public void deleteInvitation(Long id) {
        invitationDao.deleteById(id);
    }

    @Override
    public List<Invitation> findByUser(User user) {
        return invitationDao.findByUser(user);
    }

    @Override
    public List<Invitation> findByUserInstitution(User user) {
        return invitationDao.findByUserInstitution(user);
    }
}
