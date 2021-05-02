package com.demcare.demo.controller;

import com.demcare.demo.entities.UserEntity;
import com.demcare.demo.service.UserService;
import com.demcare.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@Controller
public class UserController extends DemcareController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> createUser(@ModelAttribute UserModel userModel){

        validateRequiredParam(userModel.getMail(), "mail");
        validateRequiredParam(userModel.getPassword(), "password");
        validateRequiredParam(userModel.getName(), "name");
        validateRequiredParam(userModel.getSurname(), "surname");

        Long now = new Date().getTime();
        //userModel.setRegdate(now);
        //userModel.setLastlogin(now);

        UserModel userModelRegistred = userService.register(userModel);
        return new ResponseEntity<>(userModelRegistred, HttpStatus.OK);
    }

    @RequestMapping(value="/user/add", method=RequestMethod.POST )
    public String setUser(@RequestParam String nombre,
                          @RequestParam String apellido1,
                          @RequestParam String apellido2){
        return "redirect:/";
    }

    @RequestMapping("/user/add" )
    public String getListado(Model model){
        model.addAttribute("user", new UserEntity());
       //TODO: error
        return "add";
    }


}
