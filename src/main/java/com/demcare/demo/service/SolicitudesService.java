package com.demcare.demo.service;

import com.demcare.demo.entities.SolicitudesInstitutions;
import com.demcare.demo.entities.User;

import java.util.List;

public interface SolicitudesService {
     void deleteSolicitude(Long id);
     List<SolicitudesInstitutions> findByUser(User user);
     List<SolicitudesInstitutions> findByUserInstitution(User user);
}
