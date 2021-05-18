package com.demcare.demo.service.impl;


import com.demcare.demo.dao.*;
import com.demcare.demo.entities.*;
import com.demcare.demo.service.AssociationCarerPlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociationCarerPlayerServiceImpl implements AssociationCarerPlayerService {

    @Autowired
    private AssociationCarerPlayerDao associationCarerPlayerDao;

    @Override
    public AssociationCarerPlayer save(AssociationCarerPlayer associationCarerPlayer) {
        return associationCarerPlayerDao.save(associationCarerPlayer);
    }



}
