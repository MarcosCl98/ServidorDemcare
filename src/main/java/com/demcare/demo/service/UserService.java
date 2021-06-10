package com.demcare.demo.service;

import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByMail(String mail);
    User findByName(String name);
    User findById(Long id);
    User register(User userEntity);
    User save(User user);

    void deleteUser(Long id);
    void suspendUser(Long id);
    void activateUser(Long id);
    void invitateUser(Long idInstitution, Long id);
    void solicitudeInstitution(Long idInstitution, Long id);
    void acceptInvitation(Long idInstitution, Long id);
    void acceptSolicitude(Long idInstitution, Long id);
    void asociateCarerPlayer(Long idCarer, Long idPlayer);

    List<User> getAdminList();
    List<User> getCarerList();
    List<User> getCarerListWithoutUserSession(User user);
    List<User> getPlayerList();
    List<User> getCarerAndPlayerList();
    List<User> getInstitutions();
    List<User> getInstitutionsAssociated(User user);

    List<User> getInvitations(Long idUser);
    List<User> getSolicitudes(Long idUser);

    List<User> getAssociateCarers(Long idInstitution);
    List<User> getAssociateCarersWithoutUserAuthenticated(List<User> institucionesAsociadas, Long idUser);
    List<User> getAssociateUsers(Long idInstitution);

    /*Asociados a la institución pero no a un cuidador*/
    List<User> getNotAssociatedPlayersWithCarer(List<User> jugadoresNoAsociados, Long idInstitution);
    List<User> getNotAssociatedPlayers(User user);

    List<User> getAssociatedUsers(User cuidador1, User cuidador2);
    List<User> getAssociatedUsersToCarer(User cuidador);

    List<User> getInstitutionsWithAsociation(Long idUser);
    List<User> getRequestInstitutions(Long idUser);
    List<User> getNotRequestInstitutions(Long idUser);

    List<User> getUsersWithInvitationAndRequest(Long idInstitution);
    List<User> getUsersWithoutAsociation(Long idInstitution);
    List<User> getUsersWithoutInvitation(Long idInstitution);

    void addAsociationGames(User user);
}
