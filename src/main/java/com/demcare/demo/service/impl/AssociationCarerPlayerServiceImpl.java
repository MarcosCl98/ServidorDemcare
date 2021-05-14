package com.demcare.demo.service.impl;

import com.demcare.demo.config.PasswordEncoderBean;
import com.demcare.demo.dao.*;
import com.demcare.demo.entities.*;
import com.demcare.demo.service.AssociationCarerPlayerService;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssociationCarerPlayerServiceImpl implements AssociationCarerPlayerService {

    @Autowired
    private AssociationCarerPlayerDao associationCarerPlayerDao;



    @Override
    public AssociationCarerPlayer save(AssociationCarerPlayer associationCarerPlayer) {
        return associationCarerPlayerDao.save(associationCarerPlayer);
    }
}
