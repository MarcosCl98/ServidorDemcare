package com.demcare.demo.service;

import com.demcare.demo.entities.InvitationsInstitutions;
import com.demcare.demo.entities.User;

import java.util.List;

public interface InvitationsService {
     void deleteInvitation(Long id);
     List<InvitationsInstitutions> findByUser(User user);
     List<InvitationsInstitutions> findByUserInstitution(User user);
}
