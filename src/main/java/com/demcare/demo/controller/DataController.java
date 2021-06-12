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
        String parts[] = dataModel.getUrlPlayer().split("/");
        String idUser = parts[parts.length-1].substring(1);
        Game game = gameService.findById(dataModel.getGame());
        User user = userService.findById(Long.parseLong(idUser));

        Data data = new Data();
        data.setGame(game);
        data.setScene(dataModel.getScene());
        data.setUser(user);

        if(dataModel.getTimeOpened()>0){
            data.setTime_opened(dataModel.getTimeOpened());
        }

        if(dataModel.getNumberOfClicks()>0){
            data.setNumber_clicks(dataModel.getNumberOfClicks());
        }
        if(dataModel.getMaxSpeed()>0){
            data.setMax_speed(dataModel.getMaxSpeed());
        }
        if(dataModel.getMaxAceleration()>0){
            data.setMax_acceleration(dataModel.getMaxAceleration());
        }
        if(dataModel.getNumberOfErrors()>0){
            data.setNumber_errors(dataModel.getNumberOfErrors());
        }


        dataService.save(data);
        return "/home";
    }



}
