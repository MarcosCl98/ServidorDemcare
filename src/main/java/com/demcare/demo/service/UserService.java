package com.demcare.demo.service;

import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByMail(String mail);
    User findById(Long id);
    void deleteUser(Long id);
    void suspendUser(Long id);
    void activateUser(Long id);
    void invitateUser(Long idInstitution, Long id);
    void sentSolicitude(Long idInstitution, Long id);
    void acceptInvitation(Long idInstitution, Long id);
    void acceptSolicitude(Long idInstitution, Long id);
    void asociateCarerPlayer(Long idCarer, Long idPlayer);
    User register(User userEntity);
    List<User> getUsersList();
    List<User> getCuidadores();
    List<User> getJugadores();
    List<User> getPosibleAsociatedUsers();
    List<User> getInstitutions();
    List<User> getInvitations(Long idUser);
    List<User> getSolicitudes(Long idUser);
    List<User> getCuidadoresAsociados(Long idInstitution);
    List<User> getJugadoresNoAsociadosAsociados(List<User> jugadoresNoAsociados, Long idInstitution);
    List<User> getUsersWithAsociation(Long idInstitution);
    List<User> getInstitutionsWithAsociation(Long idUser);
    List<User> getUsersWithInvitationAndRequest(Long idInstitution);
    List<User> getUsersWithoutAsociation(Long idInstitution);
    List<User> getUsersWithoutInvitation(Long idInstitution);
    List<User> getRequestInstitutions(Long idUser);
    List<User> getNotRequestInstitutions(Long idUser);
    List<User> getJugadoresNoAsociados(User user);
    User save(User user);

}
