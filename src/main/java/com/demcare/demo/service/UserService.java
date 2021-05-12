package com.demcare.demo.service;

import com.demcare.demo.entities.User;
import java.util.List;

public interface UserService {
    User findByMail(String mail);
    void deleteUser(Long id);
    void suspendUser(Long id);
    void activateUser(Long id);
    void asociateUser(Long idInstitution, Long id);
    void invitateUser(Long idInstitution, Long id);
    void acceptInvitation(Long idInstitution, Long id);
    User register(User userEntity);
    List<User> getUsers();
    List<User> getUsersList();
    List<User> getPosibleAsociatedUsers();
    List<User> getPosibleAsociatedInstitutions();
    List<User> getAsociatedUsers(Long idInstitution);
    List<User> getNotAsociatedUsers(Long idInstitution);
    List<User> getInstitutionsAsociated(Long idUser);
    List<User> getInvitatedUsers(Long idInstitution);
    List<User> getNotInvitatedUsers(Long idInstitution);
    List<User> getInvitations(Long idUser);
    User save(User user);

}
