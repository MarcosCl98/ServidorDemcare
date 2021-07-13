package com.demcare.demo;

import com.demcare.demo.controller.CarerController;
import com.demcare.demo.controller.HomeController;
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
class CasosDeUso11 {

    @InjectMocks
    private HomeController homeController;

    @InjectMocks
    private CarerController carerController;

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
    void enviarSolicitud() throws Exception {
        String result = carerController.sentSolicitude(1L);
        assertEquals(result,"redirect:/cuidador/listInstitutions");
    }
}
