package com.demcare.demo;

import com.demcare.demo.controller.AdminController;
import com.demcare.demo.controller.HomeController;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CasosDeUso3 {

    @InjectMocks
    private HomeController homeController;

    @InjectMocks
    private AdminController adminController;

    @Mock
    UserService userService;

    @Mock
    GameService gameService;

    @Mock
    AssociationInstitutionGameService associationInstitutionGameService;

    @Mock
    SecurityService securityService;

    @Test
    void agregarJuego() throws Exception {
        Game game = new Game();
        game.setUrl("http://prueba.com");
        game.setDescripcion("descripcion test");
        game.setTitulo("Test game");
        String result = adminController.setGame(game);
        assertEquals(result,"redirect:/home");
    }
}
