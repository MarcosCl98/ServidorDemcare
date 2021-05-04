package com.demcare.demo.service.impl;

import com.demcare.demo.config.PasswordEncoderBean;
import com.demcare.demo.dao.UserDao;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Override
    public User findByMail(String mail) {
        return userDao.findByMail(mail);
    }

    @Override
    @Transactional
    public void deleteByMail(String mail) {
        userDao.deleteByMail(mail);
    }

    @Override
    public User register(User user) {
        if(userDao.findByMail(user.getMail()) != null) {
            //TODO: hacer expceciones y validaciones
            //throw new EEmailExistsException
        }
        user.setPassword(passwordEncoderBean.encoder().encode(user.getPassword()));

        return user;
    }

    @Override
    public User update(User User) {
        return null;
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
}
