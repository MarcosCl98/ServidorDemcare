package com.demcare.demo.service;

import com.demcare.demo.entities.Solicitude;
import com.demcare.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SolicitudeService {
     void deleteSolicitude(Long id);
     List<Solicitude> findByUser(User user);
     List<Solicitude> findByUserInstitution(User user);
}
