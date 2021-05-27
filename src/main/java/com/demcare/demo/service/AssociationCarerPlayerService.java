package com.demcare.demo.service;

import com.demcare.demo.entities.AssociationCarerPlayer;
import com.demcare.demo.entities.AssociationInstitutionUser;
import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssociationCarerPlayerService {
    AssociationCarerPlayer save(AssociationCarerPlayer associationCarerPlayer);
    void deleteAssociationCarerPlayer(Long id);
    List<AssociationCarerPlayer> findByPlayerUser(User user);
    List<AssociationCarerPlayer> findByCarerUser(User user);
}
