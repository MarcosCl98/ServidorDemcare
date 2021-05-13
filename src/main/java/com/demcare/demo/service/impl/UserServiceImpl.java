package com.demcare.demo.service.impl;

import com.demcare.demo.config.PasswordEncoderBean;
import com.demcare.demo.dao.AsociatedUserDao;
import com.demcare.demo.dao.InvitationsDao;
import com.demcare.demo.dao.SolicitudesDao;
import com.demcare.demo.dao.UserDao;
import com.demcare.demo.entities.AsociatedUser;
import com.demcare.demo.entities.InvitationsInstitutions;
import com.demcare.demo.entities.SolicitudesInstitutions;
import com.demcare.demo.entities.User;
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
    private AsociatedUserDao asociatedUserDao;

    @Autowired
    private InvitationsDao invitationsInstitutionsDao;

    @Autowired
    private SolicitudesDao solicitudesDao;

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Override
    public User findByMail(String mail) {
        return userDao.findByMail(mail);
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
        userDao.deleteById(id);
        userDao.save(user.get());
    }

    @Override
    public void activateUser(Long id) {
        Optional<User> user = userDao.findById(id);
        user.get().setSuspend(false);
        userDao.deleteById(id);
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
    public void sentSolicitude(Long idInstitution, Long id) {
        SolicitudesInstitutions solicitud = new SolicitudesInstitutions();
        solicitud.setUserInstitution(userDao.findById(id).get());
        solicitud.setUser( userDao.findById(idInstitution).get());
        solicitudesDao.save(solicitud);
    }

    @Override
    public void acceptInvitation(Long idInstitution, Long id) {
        AsociatedUser asociacion = new AsociatedUser();
        asociacion.setUser( userDao.findById(id).get());
        asociacion.setUserInstitution( userDao.findById(idInstitution).get());
        asociatedUserDao.save(asociacion);
        List<InvitationsInstitutions> list =  invitationsInstitutionsDao.findAll();
        for(InvitationsInstitutions l: list){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                invitationsInstitutionsDao.deleteById(l.getId());
            }
        }
        List<SolicitudesInstitutions> list2 =  solicitudesDao.findAll();
        for(SolicitudesInstitutions l: list2){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                solicitudesDao.deleteById(l.getId());
            }
        }
    }

    @Override
    public void acceptSolicitude(Long idInstitution, Long id) {
        AsociatedUser asociacion = new AsociatedUser();
        asociacion.setUser( userDao.findById(id).get());
        asociacion.setUserInstitution( userDao.findById(idInstitution).get());
        asociatedUserDao.save(asociacion);
        List<SolicitudesInstitutions> list =  solicitudesDao.findAll();
        for(SolicitudesInstitutions l: list){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                solicitudesDao.deleteById(l.getId());
            }
        }
        List<InvitationsInstitutions> list2 =  invitationsInstitutionsDao.findAll();
        for(InvitationsInstitutions l: list2){
            if(l.getUser().getId() == id && l.getUserInstitution().getId() == idInstitution){
                invitationsInstitutionsDao.deleteById(l.getId());
            }
        }
    }


    @Override
    public List<User> getUsersList() {
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
    public List<User> getPosibleAsociatedUsers() {
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
    public List<User> getUsersWithAsociation(Long idInstitution) {
        Iterable<User>  userList = getPosibleAsociatedUsers();
        Iterable<AsociatedUser> iterable = asociatedUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean asociado = false;
            for (AsociatedUser it : iterable) {
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
    public List<User> getInstitutionsWithAsociation(Long idUser) {
        Iterable<User>  userList = getInstitutions();
        Iterable<AsociatedUser> iterable = asociatedUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            for (AsociatedUser it : iterable) {
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
        Iterable<User>  userList = getPosibleAsociatedUsers();
        Iterable<AsociatedUser> iterable = asociatedUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean asociado = false;
            for (AsociatedUser it : iterable) {
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

        Iterable<AsociatedUser> iterable2 = asociatedUserDao.findAll();
        for(User usuariosWithoutRequest: list){
            boolean asociada = false;
            for(AsociatedUser asociation: iterable2){
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

}
