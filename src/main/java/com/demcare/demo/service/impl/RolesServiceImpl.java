package com.demcare.demo.service.impl;

import com.demcare.demo.service.RolesService;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements RolesService {

    String[] roles = {"ROLE_ADMIN","ROLE_CUIDADOR","ROLE_INSTITUCION","ROLE_JUGADOR"};

    @Override
    public String[] getRoles() {
        return roles;
    }
}
