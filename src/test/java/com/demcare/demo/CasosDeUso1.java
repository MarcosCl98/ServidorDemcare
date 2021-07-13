package com.demcare.demo;

import com.demcare.demo.controller.AdminController;
import com.demcare.demo.controller.HomeController;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CasosDeUso1 {

    @InjectMocks
    private HomeController homeController;

    @InjectMocks
    private AdminController adminController;

    @Mock
    UserService userService;

    @Mock
    AssociationInstitutionUserService associationInstitutionUserService;

    @Mock
    AssociationCarerPlayerService associationCarerPlayerService;

    @Mock
    TokenService tokenService;

    @Mock
    SolicitudeService solicitudeService;

    @Mock
    InvitationService invitationService;

    @Mock
    AssociationInstitutionGameService associationInstitutionGameService;

    @Mock
    SecurityService securityService;

    @Test
    @WithMockUser("demcare1345")
    void eliminarCuenta() throws Exception {
        String result = adminController.deleteUser(1L);
        assertEquals(result,"redirect:/admin/list");
    }
}
