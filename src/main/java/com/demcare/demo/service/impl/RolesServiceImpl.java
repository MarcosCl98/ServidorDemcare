package com.demcare.demo.service.impl;

import com.demcare.demo.service.RolesService;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements RolesService {

    String[] roles = {"ROLE_ADMIN","ROLE_CUIDADOR","ROLE_INSTITUCION","ROLE_JUGADOR"};
    String[] rolesSimple = {"ADMIN","CUIDADOR","INSTITUCION","JUGADOR"};
    String[] roles2 = {"JUGADOR"};
    @Override
    public String[] getRoles() {
        return roles;
    }

    @Override
    public String[] getRolesSimple() {
        return rolesSimple;
    }

    @Override
    public String[] getRoleJugador() {
        return roles2;
    }
}
