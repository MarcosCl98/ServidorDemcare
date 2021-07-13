package com.demcare.demo.service;


import com.demcare.demo.entities.Invitation;
import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface InvitationService {
     void deleteInvitation(Long id);
     List<Invitation> findByUser(User user);
     List<Invitation> findByUserInstitution(User user);
}
