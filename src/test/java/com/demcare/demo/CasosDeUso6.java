package com.demcare.demo;

import com.demcare.demo.controller.HomeController;
import com.demcare.demo.controller.InstitutionController;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.AssociationInstitutionGameService;
import com.demcare.demo.service.GameService;
import com.demcare.demo.service.SecurityService;
import com.demcare.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
class CasosDeUso6 {

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

    @Test
    @WithMockUser("demcare1345")
    void invitarUsuario() throws Exception {
        String result = institutionController.inviteUser(1L);
        assertEquals(result,"redirect:/institution/listUsersToInvite");
    }
}
