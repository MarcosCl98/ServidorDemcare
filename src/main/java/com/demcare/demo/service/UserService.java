package com.demcare.demo.service;

import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByMail(String mail);
    void deleteUser(Long id);
    void suspendUser(Long id);
    void activateUser(Long id);
    void invitateUser(Long idInstitution, Long id);
    void sentSolicitude(Long idInstitution, Long id);
    void acceptInvitation(Long idInstitution, Long id);
    void acceptSolicitude(Long idInstitution, Long id);
    User register(User userEntity);
    List<User> getUsersList();
    List<User> getPosibleAsociatedUsers();
    List<User> getInstitutions();
    List<User> getInvitations(Long idUser);
    List<User> getSolicitudes(Long idUser);
    List<User> getUsersWithAsociation(Long idInstitution);
    List<User> getInstitutionsWithAsociation(Long idUser);
    List<User> getUsersWithInvitationAndRequest(Long idInstitution);
    List<User> getUsersWithoutAsociation(Long idInstitution);
    List<User> getUsersWithoutInvitation(Long idInstitution);
    List<User> getRequestInstitutions(Long idUser);
    List<User> getNotRequestInstitutions(Long idUser);
    User save(User user);

}
