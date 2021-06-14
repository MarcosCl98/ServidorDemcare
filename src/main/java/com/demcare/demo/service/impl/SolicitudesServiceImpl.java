package com.demcare.demo.service.impl;

import com.demcare.demo.dao.SolicitudesDao;
import com.demcare.demo.entities.SolicitudesInstitutions;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.SolicitudesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudesServiceImpl implements SolicitudesService {

    @Autowired
    SolicitudesDao solicitudesDao;

    @Override
    public void deleteSolicitude(Long id) {
        solicitudesDao.deleteById(id);
    }

    @Override
    public List<SolicitudesInstitutions> findByUser(User user) {
        return solicitudesDao.findByUser(user);
    }

    @Override
    public List<SolicitudesInstitutions> findByUserInstitution(User user) {
        return solicitudesDao.findByUserInstitution(user);
    }
}
