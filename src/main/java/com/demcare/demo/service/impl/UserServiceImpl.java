package com.demcare.demo.service.impl;

import com.demcare.demo.config.PasswordEncoderBean;
import com.demcare.demo.dao.AsociatedUserDao;
import com.demcare.demo.dao.UserDao;
import com.demcare.demo.entities.AsociatedUser;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public void asociateUser(Long idInstitution, Long id) {
        AsociatedUser asociacion = new AsociatedUser();
        asociacion.setUser( userDao.findById(id).get());
        asociacion.setUserInstitution( userDao.findById(idInstitution).get());
        asociatedUserDao.save(asociacion);
    }

    @Override
    public List<User> getUsers() {
        Iterable<User> iterable = userDao.findAll();
        List <User> list = new ArrayList<User>();
        for (User item : iterable) {
            list.add( item);
        }
        return list;
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
    public List<User> getAsociatedUsers() {
        Iterable<AsociatedUser> iterable = asociatedUserDao.findAll();
        List <User> list = new ArrayList<User>();
        for (AsociatedUser item : iterable) {
            list.add(item.getUser());
        }
        return list;
    }

    @Override
    public List<User> getNotAsociatedUsers() {
        Iterable<User>  userList = getPosibleAsociatedUsers();
        Iterable<AsociatedUser> iterable = asociatedUserDao.findAll();
        List <User> list = new ArrayList<User>();

        for (User item : userList) {
            boolean asociado = false;
            for (AsociatedUser it : iterable) {
                if(it.getUser().getId() == item.getId()){
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
    public User save(User user) {
        return userDao.save(user);
    }



}
