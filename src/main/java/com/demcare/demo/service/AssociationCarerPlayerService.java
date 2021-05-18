package com.demcare.demo.service;

import com.demcare.demo.entities.AssociationCarerPlayer;
import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssociationCarerPlayerService {
    AssociationCarerPlayer save(AssociationCarerPlayer associationCarerPlayer);
}
