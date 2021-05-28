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
    private InvitationsDao invitationsInstitutionsDao;

    @Autowired
    private SolicitudesDao solicitudesDao;

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findByMail(String mail) {
        return userDao.findByMail(mail);
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
        if(userDao.findByMail(user.getMail()) != null) {
            //TODO: hacer expceciones y validaciones
            //throw new EEmailExistsException
        }
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
        InvitationsInstitutions invitacion = new InvitationsInstitutions();
        invitacion.setUser( userDao.findById(id).get());
        invitacion.setUserInstitution( userDao.findById(idInstitution).get());
        invitationsInstitutionsDao.save(invitacion);
    }

    @Override
    public void solicitudeInstitution(Long idInstitution, Long id) {
        SolicitudesInstitutions solicitud = new SolicitudesInstitutions();
        solicitud.setUserInstitution(userDao.findById(id).get());
        solicitud.setUser( userDao.findById(idInstitution).get());
        solicitudesDao.save(solicitud);
    }

    @Override
    public void acceptInvitation(Long idInstitution, Long id) {
        AssociationInstitutionUser asociacion = new AssociationInstitutionUser();
        asociacion.setUser( userDao.findById(id).get());
        asociacion.setUserInstitution( userDao.findById(idInstitution).get());
        asociationInstitutionUserDao.save(asociacion);
        Iterable<InvitationsInstitutions> list =  invitationsInstitutionsDao.findAll();
        for(InvitationsInstitutions l: list){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                invitationsInstitutionsDao.deleteById(l.getId());
            }
        }
        Iterable<SolicitudesInstitutions> list2 =  solicitudesDao.findAll();
        for(SolicitudesInstitutions l: list2){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                solicitudesDao.deleteById(l.getId());
            }
        }
    }

    @Override
    public void acceptSolicitude(Long idInstitution, Long id) {
        AssociationInstitutionUser asociacion = new AssociationInstitutionUser();
        asociacion.setUser( userDao.findById(id).get());
        asociacion.setUserInstitution( userDao.findById(idInstitution).get());
        asociationInstitutionUserDao.save(asociacion);
        Iterable<SolicitudesInstitutions> list =  solicitudesDao.findAll();
        for(SolicitudesInstitutions l: list){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                solicitudesDao.deleteById(l.getId());
            }
        }
        Iterable<InvitationsInstitutions> list2 =  invitationsInstitutionsDao.findAll();
        for(InvitationsInstitutions l: list2){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                invitationsInstitutionsDao.deleteById(l.getId());
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
        Iterable<InvitationsInstitutions> iterable = invitationsInstitutionsDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean invitado = false;
            for (InvitationsInstitutions it : iterable) {
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
        Iterable<InvitationsInstitutions> iterable = invitationsInstitutionsDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean invitado = false;
            for (InvitationsInstitutions it : iterable) {
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
        Iterable<InvitationsInstitutions> iterable = invitationsInstitutionsDao.findAll();
        List <User> list = new ArrayList<User>();

        for (InvitationsInstitutions it : iterable) {
            if(it.getUser().getId() == idUser){
                list.add(it.getUserInstitution());
            }
        }

        return list;
    }

    @Override
    public List<User> getSolicitudes(Long idUser) {
        Iterable<SolicitudesInstitutions> iterable = solicitudesDao.findAll();
        List <User> list = new ArrayList<User>();

        for (SolicitudesInstitutions it : iterable) {
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
        Iterable<SolicitudesInstitutions> iterable = solicitudesDao.findAll();
        List <User> listAllInstitutions = getInstitutions();
        List <User> list = new ArrayList<User>();

        for(User institution: listAllInstitutions){
            boolean solicitada = false;
            for(SolicitudesInstitutions solicitude: iterable){
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
        Iterable<SolicitudesInstitutions> iterable = solicitudesDao.findAll();
        List <User> listAllInstitutions = getInstitutions();
        List <User> list = new ArrayList<User>();
        List <User> list2 = new ArrayList<User>();
        for(User institution: listAllInstitutions){
            boolean solicitada = false;
            for(SolicitudesInstitutions solicitude: iterable){
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
        Iterable<AssociationCarerPlayer> iterable = associationCarerPlayerDao.findAll();
        List <User> list = new ArrayList<User>();
        List <User> allJugadores = getPlayerList();
        List <User> list2 = new ArrayList<User>();
        List <User> finalList = new ArrayList<User>();
        for(AssociationCarerPlayer a: iterable){
            if(a.getCarerUser().getId() == user.getId()){
                list.add(a.getPlayerUser());
            }
        }

        for(User jugador: allJugadores){
            boolean asociado = false;
            for(User jugadorAsociado: list){
                if(jugador.getId() == jugadorAsociado.getId()){
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

}
