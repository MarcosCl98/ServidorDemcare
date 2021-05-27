package com.demcare.demo.service.impl;


import com.demcare.demo.dao.*;
import com.demcare.demo.entities.*;
import com.demcare.demo.service.AssociationCarerPlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssociationCarerPlayerServiceImpl implements AssociationCarerPlayerService {

    @Autowired
    private AssociationCarerPlayerDao associationCarerPlayerDao;

    @Override
    public AssociationCarerPlayer save(AssociationCarerPlayer associationCarerPlayer) {
        return associationCarerPlayerDao.save(associationCarerPlayer);
    }

    @Override
    public void deleteAssociationCarerPlayer(Long id) {
        associationCarerPlayerDao.deleteById(id);
    }

    @Override
    public List<AssociationCarerPlayer> findByPlayerUser(User user) {
        return associationCarerPlayerDao.findByPlayerUser(user);
    }

    @Override
    public List<AssociationCarerPlayer> findByCarerUser(User user) {
        return associationCarerPlayerDao.findByCarerUser(user);
    }


}
