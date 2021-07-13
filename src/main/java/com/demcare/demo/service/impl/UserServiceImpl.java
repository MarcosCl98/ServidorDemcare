package com.demcare.demo.service.impl;

import com.demcare.demo.config.PasswordEncoderBean;
import com.demcare.demo.dao.*;
import com.demcare.demo.entities.*;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private AssociationInstitutionGameDao associationInstitutionGameDao;

    @Autowired
    private AssociationInstitutionUserDao asociationInstitutionUserDao;

    @Autowired
    private AssociationCarerPlayerDao associationCarerPlayerDao;

    @Autowired
    private InvitationDao invitationsInstitutionsDao;

    @Autowired
    private SolicitudeDao solicitudeDao;

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoderBean.encoder().encode(user.getPassword()));
        userDao.save(user);
        return user;
    }

    @Override
    public void suspendUser(Long id) {
        Optional<User> user = userDao.findById(id);
        user.get().setSuspend(true);
        userDao.save(user.get());
    }

    @Override
    public void activateUser(Long id) {
        Optional<User> user = userDao.findById(id);
        user.get().setSuspend(false);
        userDao.save(user.get());
    }

    @Override
    public void invitateUser(Long idInstitution, Long id) {
        Invitation invitacion = new Invitation();
        invitacion.setUser( userDao.findById(id).get());
        invitacion.setUserInstitution( userDao.findById(idInstitution).get());
        invitationsInstitutionsDao.save(invitacion);
    }

    @Override
    public void solicitudeInstitution(Long idInstitution, Long id) {
        Solicitude solicitud = new Solicitude();
        solicitud.setUserInstitution(userDao.findById(id).get());
        solicitud.setUser( userDao.findById(idInstitution).get());
        solicitudeDao.save(solicitud);
    }

    @Override
    public void acceptInvitation(Long idInstitution, Long id) {
        AssociationInstitutionUser asociacion = new AssociationInstitutionUser();
        asociacion.setUser( userDao.findById(id).get());
        asociacion.setUserInstitution( userDao.findById(idInstitution).get());
        asociationInstitutionUserDao.save(asociacion);
        Iterable<Invitation> list =  invitationsInstitutionsDao.findAll();
        for(Invitation l: list){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                invitationsInstitutionsDao.deleteById(l.getId());
            }
        }
        Iterable<Solicitude> list2 =  solicitudeDao.findAll();
        for(Solicitude l: list2){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                solicitudeDao.deleteById(l.getId());
            }
        }
    }

    @Override
    public void rejectInvitation(Long idInstitution, Long id) {
        Iterable<Invitation> list =  invitationsInstitutionsDao.findByUserInstitution(userDao.findById(idInstitution).get());
        for(Invitation l: list){
            if(l.getUser().getId() == id){
                invitationsInstitutionsDao.deleteById(l.getId());
            }
        }
        Iterable<Solicitude> list2 =  solicitudeDao.findByUserInstitution(userDao.findById(idInstitution).get());
        for(Solicitude l: list2){
            if(l.getUser().getId() == id){
                solicitudeDao.deleteById(l.getId());
            }
        }
    }

    @Override
    public void acceptSolicitude(Long idInstitution, Long id) {
        AssociationInstitutionUser asociacion = new AssociationInstitutionUser();
        asociacion.setUser( userDao.findById(id).get());
        asociacion.setUserInstitution( userDao.findById(idInstitution).get());
        asociationInstitutionUserDao.save(asociacion);
        Iterable<Solicitude> list =  solicitudeDao.findAll();
        for(Solicitude l: list){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                solicitudeDao.deleteById(l.getId());
            }
        }
        Iterable<Invitation> list2 =  invitationsInstitutionsDao.findAll();
        for(Invitation l: list2){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                invitationsInstitutionsDao.deleteById(l.getId());
            }
        }
    }

    @Override
    public void rejectSolicitude(Long idInstitution, Long id) {
        Iterable<Invitation> list =  invitationsInstitutionsDao.findByUserInstitution(userDao.findById(idInstitution).get());
        for(Invitation l: list){
            if(l.getUser().getId() == id){
                invitationsInstitutionsDao.deleteById(l.getId());
            }
        }
        Iterable<Solicitude> list2 =  solicitudeDao.findByUserInstitution(userDao.findById(idInstitution).get());
        for(Solicitude l: list2){
            long l2 = l.getUser().getId();
            if(l2 == id){
                solicitudeDao.deleteById(l.getId());
            }
        }
    }

    @Override
    public void asociateCarerPlayer(Long idCarer, Long idPlayer) {
        AssociationCarerPlayer association = new AssociationCarerPlayer();
        association.setPlayerUser(userDao.findById(idPlayer).get());
        association.setCarerUser(userDao.findById(idCarer).get());
        associationCarerPlayerDao.save(association);
    }

    @Override
    public List<User> getAdminList() {
        Iterable<User> iterable = userDao.findAll();
        List <User> list = new ArrayList<User>();
        for (User item : iterable) {
            if(!item.getRole().equals("ROLE_ADMIN")){
                list.add( item);
            }
        }
        return list;
    }

    @Override
    public List<User> getCarerList() {
        Iterable<User> iterable = userDao.findAll();
        List <User> list = new ArrayList<User>();
        for (User item : iterable) {
            if(item.getRole().equals("ROLE_CUIDADOR")){
                list.add( item);
            }
        }
        return list;
    }

    @Override
    public List<User> getCarerListWithoutUserSession(User user) {
        Iterable<User> iterable = userDao.findAll();
        List <User> list = new ArrayList<User>();
        for (User item : iterable) {
            if(item.getRole().equals("ROLE_CUIDADOR") && item.getId() != user.getId()){
                list.add( item);
            }
        }
        return list;
    }

    @Override
    public List<User> getPlayerList() {
        Iterable<User> iterable = userDao.findAll();
        List <User> list = new ArrayList<User>();
        for (User item : iterable) {
            if(item.getRole().equals("ROLE_JUGADOR")){
                list.add( item);
            }
        }
        return list;
    }

    @Override
    public List<User> getCarerAndPlayerList() {
        Iterable<User> iterable = userDao.findAll();
        List <User> list = new ArrayList<User>();
        for (User item : iterable) {
            if(!item.getRole().equals("ROLE_ADMIN") && !item.getRole().equals("ROLE_INSTITUCION")){
                list.add( item);
            }
        }
        return list;
    }

    @Override
    public List<User> getInstitutions() {
        Iterable<User> iterable = userDao.findAll();
        List <User> list = new ArrayList<User>();
        for (User item : iterable) {
            if(item.getRole().equals("ROLE_INSTITUCION")){
                list.add( item);
            }
        }
        return list;
    }

    @Override
    public List<User> getInstitutionsAssociated(User user) {
        Iterable<AssociationInstitutionUser> iterable = asociationInstitutionUserDao.findAll();
        List <User> list = new ArrayList<User>();
        for (AssociationInstitutionUser item : iterable) {
           if(item.getUser().getId() == user.getId()){
               list.add(item.getUserInstitution());
           }
        }
        return list;
    }

    @Override
    public List<User> getAssociateUsers(Long idInstitution) {
        Iterable<User>  userList = getCarerAndPlayerList();
        Iterable<AssociationInstitutionUser> iterable = asociationInstitutionUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean asociado = false;
            for (AssociationInstitutionUser it : iterable) {
                if(it.getUser().getId() == item.getId() && idInstitution == it.getUserInstitution().getId()){
                    asociado = true;
                }
            }
            if(asociado){
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<User> getUsersWithoutAsocciationWithAInstitution(List<User> users) {
        Iterable<AssociationInstitutionUser> associationInstitutionUser = asociationInstitutionUserDao.findAll();
        List<User> finalList = new ArrayList<>();
        for(User u: users){
            boolean asociado = false;
            for(AssociationInstitutionUser a: associationInstitutionUser){
                if(a.getUser().getId() == u.getId()){
                    asociado = true;
                }
            }
            if(!asociado){
                finalList.add(u);
            }
        }

        return finalList;
    }

    @Override
    public List<User> getAssociateCarers(Long idInstitution) {
        Iterable<User>  userList = getCarerList();
        Iterable<AssociationInstitutionUser> iterable = asociationInstitutionUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean asociado = false;
            for (AssociationInstitutionUser it : iterable) {
                if(it.getUser().getId() == item.getId() && idInstitution == it.getUserInstitution().getId()){
                    asociado = true;
                }
            }
            if(asociado){
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<User> getAssociateCarersWithoutUserAuthenticated(List<User> institucionesAsociadas, Long idUser) {
        List<User> finalList = new ArrayList<User>();
        for(User u: institucionesAsociadas){
            List<User> list =  getAssociateCarers(u.getId());
            for(User ulist: list){
                if(ulist.getId() != idUser){
                    boolean contiene = false;
                    for(User userList: finalList){
                        if(userList.getId() == ulist.getId()){
                            contiene= true;
                        }
                    }
                    if(!contiene){
                        finalList.add(ulist);
                    }

                }
            }
        }
        return finalList;
    }

    @Override
    public List<User> getNotAssociatedPlayersWithCarer(List<User> jugadoresNoAsociados, Long idInstitution) {
        Iterable<AssociationInstitutionUser> iterable = asociationInstitutionUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User jugadorNoAsociado : jugadoresNoAsociados) {
            boolean asociado = false;
            for (AssociationInstitutionUser asociacion : iterable) {
                if(asociacion.getUserInstitution().getId() == idInstitution && asociacion.getUser().getId() == jugadorNoAsociado.getId()){
                    asociado = true;
                }
            }
            if(asociado){
                list.add(jugadorNoAsociado);
            }
        }
        return list;
    }

    @Override
    public List<User> getInstitutionsWithAsociation(Long idUser) {
        Iterable<User>  userList = getInstitutions();
        Iterable<AssociationInstitutionUser> iterable = asociationInstitutionUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            for (AssociationInstitutionUser it : iterable) {
                if(it.getUser().getId() == idUser && item.getId() == it.getUserInstitution().getId()){
                    list.add(item);
                }
            }
        }
        return list;
    }

    @Override
    public List<User> getUsersWithInvitationAndRequest(Long idInstitution) {
        Iterable<User>  userList = getUsersWithoutAsociation(idInstitution);
        Iterable<Invitation> iterable = invitationsInstitutionsDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean invitado = false;
            for (Invitation it : iterable) {
                if(it.getUser().getId() == item.getId() && it.getUserInstitution().getId() == idInstitution){
                    invitado = true;
                }
            }
            if(invitado){
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<User> getUsersWithoutAsociation(Long idInstitution) {
        Iterable<User>  userList = getCarerAndPlayerList();
        Iterable<AssociationInstitutionUser> iterable = asociationInstitutionUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean asociado = false;
            for (AssociationInstitutionUser it : iterable) {
                if(it.getUser().getId() == item.getId() && idInstitution == it.getUserInstitution().getId()){
                    asociado = true;
                }
            }
            if(!asociado){
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<User> getUsersWithoutInvitation(Long idInstitution) {
        Iterable<User>  userList = getUsersWithoutAsociation(idInstitution);
        Iterable<Invitation> iterable = invitationsInstitutionsDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean invitado = false;
            for (Invitation it : iterable) {
                if(it.getUser().getId() == item.getId() && it.getUserInstitution().getId() == idInstitution){
                    invitado = true;
                }
            }
            if(!invitado){
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public void addAsociationGames(User user) {
        Iterable<Game> gameList = gameDao.findAll();
        for(Game g: gameList){
            AssociationInstitutionGame asociacion = new AssociationInstitutionGame();
            asociacion.setGame(g);
            asociacion.setInstitution(user);
            associationInstitutionGameDao.save(asociacion);
        }
    }

    @Override
    public List<User> getInvitations(Long idUser) {
        Iterable<Invitation> iterable = invitationsInstitutionsDao.findAll();
        List <User> list = new ArrayList<User>();

        for (Invitation it : iterable) {
            if(it.getUser().getId() == idUser){
                list.add(it.getUserInstitution());
            }
        }

        return list;
    }

    @Override
    public List<User> getSolicitudes(Long idUser) {
        Iterable<Solicitude> iterable = solicitudeDao.findAll();
        List <User> list = new ArrayList<User>();
        for (Solicitude it : iterable) {
            if(it.getUserInstitution().getId() == idUser){
                list.add(it.getUser());
            }
        }
        return list;
    }


    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public List<User> getRequestInstitutions(Long idUser) {
        Iterable<Solicitude> iterable = solicitudeDao.findAll();
        List <User> listAllInstitutions = getInstitutions();
        List <User> list = new ArrayList<User>();

        for(User institution: listAllInstitutions){
            boolean solicitada = false;
            for(Solicitude solicitude: iterable){
                if(institution.getId() == solicitude.getUserInstitution().getId() && idUser == solicitude.getUser().getId()){
                    solicitada = true;
                }
            }
            if(solicitada){
                list.add(institution);
            }
        }
        return list;
    }

    @Override
    public List<User> getNotRequestInstitutions(Long idUser) {
        Iterable<Solicitude> iterable = solicitudeDao.findAll();
        List <User> listAllInstitutions = getInstitutions();
        List <User> list = new ArrayList<User>();
        List <User> list2 = new ArrayList<User>();
        for(User institution: listAllInstitutions){
            boolean solicitada = false;
            for(Solicitude solicitude: iterable){
                if(institution.getId() == solicitude.getUserInstitution().getId() && idUser == solicitude.getUser().getId()){
                    solicitada = true;
                }
            }
            if(!solicitada){
                list.add(institution);
            }
        }

        Iterable<AssociationInstitutionUser> iterable2 = asociationInstitutionUserDao.findAll();
        for(User usuariosWithoutRequest: list){
            boolean asociada = false;
            for(AssociationInstitutionUser asociation: iterable2){
                if(asociation.getUserInstitution().getId() == usuariosWithoutRequest.getId() && idUser == asociation.getUser().getId()){
                    asociada = true;
                }
            }
            if(!asociada){
                list2.add(usuariosWithoutRequest);
            }
        }
        return list2;
    }

    @Override
    public List<User> getNotAssociatedPlayers(User user) {
        Iterable<AssociationCarerPlayer> iterable = associationCarerPlayerDao.findByCarerUser(user);
        List <User> allJugadores = getPlayerList();
        List <User> list2 = new ArrayList<User>();

        for(User jugador: allJugadores){
            boolean asociado = false;
            for(AssociationCarerPlayer jugadorAsociado: iterable){
                System.out.println(jugadorAsociado.getCarerUser().getId());
                if(jugador.getId() == jugadorAsociado.getPlayerUser().getId()){
                    asociado = true;
                }
            }
            if(!asociado){
                list2.add(jugador);
            }
        }
        return list2;
    }

    @Override
    public List<User> getAssociatedUsers(User cuidador1, User cuidador2) {
        List<AssociationCarerPlayer> jugadoresAsociadosCuidador1 = associationCarerPlayerDao.findByCarerUser(cuidador1);
        List<AssociationCarerPlayer> jugadoresYaAsociadosCuidador2 = associationCarerPlayerDao.findByCarerUser(cuidador2);
        List <User> finalList = new ArrayList<User>();
        for(AssociationCarerPlayer a1: jugadoresAsociadosCuidador1){
            boolean asociado = false;
            for(AssociationCarerPlayer a2: jugadoresYaAsociadosCuidador2){
                    if(a2.getPlayerUser().getId() == a1.getPlayerUser().getId()){
                        asociado = true;
                    }
            }
            if(!asociado){
                finalList.add(a1.getPlayerUser());
            }
        }

        return finalList;
    }

    @Override
    public List<User> getAssociatedUsersToCarer(User cuidador) {
        List<AssociationCarerPlayer> jugadoresAsociadosCuidador = associationCarerPlayerDao.findByCarerUser(cuidador);
        List <User> finalList = new ArrayList<User>();
        for(AssociationCarerPlayer a: jugadoresAsociadosCuidador){
            finalList.add(a.getPlayerUser());
        }
        return finalList;
    }

}
