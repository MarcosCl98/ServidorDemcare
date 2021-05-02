package com.demcare.demo.service.impl;

import com.demcare.demo.config.PasswordEncoderBean;
import com.demcare.demo.dao.UserDao;
import com.demcare.demo.entities.UserEntity;
import com.demcare.demo.model.UserModel;
import com.demcare.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    //Mapper
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Override
    public UserModel findByMail(String mail) {
        return modelMapper.map(userDao.findByMail(mail), UserModel.class);
    }

    @Override
    @Transactional
    public void deleteByMail(String mail) {
        userDao.deleteByMail(mail);
    }

    @Override
    public UserModel register(UserModel userModel) {
        if(userDao.findByMail(userModel.getMail()) != null) {
            //TODO: hacer expceciones y validaciones
            //throw new EEmailExistsException
        }
        userModel.setPassword(passwordEncoderBean.encoder().encode(userModel.getPassword()));

        UserEntity userEntity = modelMapper.map(userModel, UserEntity.class);
        return modelMapper.map(userDao.save(userEntity), UserModel.class);
    }

    @Override
    public UserModel update(UserModel userModel) {
        UserEntity userEntity = modelMapper.map(userModel, UserEntity.class);
        return modelMapper.map(userDao.save(userEntity), UserModel.class);
    }
}
