package com.demcare.demo.controller;


import com.demcare.demo.entities.User;
import com.demcare.demo.service.*;
import com.demcare.demo.validators.SingUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin(origins = "*")
public class UserController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SingUpFormValidator signUpFormValidator;

    @RequestMapping(value = "/singup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("user", new User());
        return "singup";
    }

    @RequestMapping(value = "/singup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result, Model
            model) {

        validateRequiredParam(user.getMail(), "mail");
        validateRequiredParam(user.getPassword(), "password");
        validateRequiredParam(user.getName(), "name");
        validateRequiredParam(user.getSurname(), "surname");

        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "singup";
        }
        userService.register(user);
        if(user.getRole().equals("ROLE_INSTITUCION")){
            userService.addAsociationGames(user);
        }
        UsernamePasswordAuthenticationToken a = securityService.autoLogin(user.getMail(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }

    @RequestMapping(value = { "/games/index" }, method = RequestMethod.GET)
    public String matching(Model model) {
        return "/games/index";
    }

}
