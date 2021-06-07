package com.demcare.demo.controller;


import com.demcare.demo.entities.Data;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.User;
import com.demcare.demo.models.DataModel;
import com.demcare.demo.service.DataService;
import com.demcare.demo.service.GameService;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@CrossOrigin(origins = "*")
public class DataController extends DemcareController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private DataService dataService;


    @CrossOrigin(origins = "*")
    @PostMapping(value="/data", produces = MediaType.APPLICATION_JSON_VALUE )
    public String data(@RequestBody DataModel dataModel){
        String idUser = dataModel.getUrlPlayer().substring(31);
        Game game = gameService.findById(dataModel.getGame());
        User user = userService.findById(Long.parseLong(idUser));

        Data data = new Data();
        data.setGame(game);
        data.setScene(dataModel.getScene());
        data.setTime_opened(dataModel.getTimeOpened());
        data.setUser(user);

        dataService.save(data);
        return "/home";
    }



}
