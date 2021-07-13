package com.demcare.demo;


import com.demcare.demo.controller.HomeController;
import com.demcare.demo.controller.InstitutionController;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.AssociationInstitutionGameService;
import com.demcare.demo.service.GameService;
import com.demcare.demo.service.SecurityService;
import com.demcare.demo.service.UserService;
import com.nimbusds.jose.proc.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CasosDeUso4 {

    @InjectMocks
    private HomeController homeController;

    @InjectMocks
    private InstitutionController institutionController;

    @Mock
    UserService userService;

    @Mock
    GameService gameService;

    @Mock
    AssociationInstitutionGameService associationInstitutionGameService;

    @Mock
    SecurityService securityService;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @Test
    @WithMockUser
    void activarJuego() throws Exception {
        String result = institutionController.activate(1L);
        assertEquals(result,"redirect:/institution/listgames");
    }
}
