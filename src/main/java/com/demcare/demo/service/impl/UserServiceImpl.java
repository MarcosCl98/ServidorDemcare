package com.demcare.demo.service.impl;

import com.demcare.demo.config.PasswordEncoderBean;
import com.demcare.demo.dao.UserDao;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpSession httpSession;

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
}
