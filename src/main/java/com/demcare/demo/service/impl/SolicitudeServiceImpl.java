package com.demcare.demo.service.impl;

import com.demcare.demo.dao.SolicitudeDao;
import com.demcare.demo.entities.Solicitude;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.SolicitudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudeServiceImpl implements SolicitudeService {

    @Autowired
    SolicitudeDao solicitudeDao;

    @Override
    public void deleteSolicitude(Long id) {
        solicitudeDao.deleteById(id);
    }

    @Override
    public List<Solicitude> findByUser(User user) {
        return solicitudeDao.findByUser(user);
    }

    @Override
    public List<Solicitude> findByUserInstitution(User user) {
        return solicitudeDao.findByUserInstitution(user);
    }
}
