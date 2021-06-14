package com.demcare.demo.controller;


import com.demcare.demo.entities.User;
import com.demcare.demo.service.*;
import com.demcare.demo.validators.SingUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@CrossOrigin(origins = "*")
public class HomeController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SingUpFormValidator signUpFormValidator;

    @RequestMapping(value = "/singup", method = RequestMethod.GET)
    public String signup(Model model) {
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
        if(user.getRole().equals("INSTITUCION") || user.getRole().equals("INSTITUTION") ){
            user.setRole("ROLE_INSTITUCION");
        } else if(user.getRole().equals("CUIDADOR") || user.getRole().equals("CARER") ){
            user.setRole("ROLE_CUIDADOR");
        } else {
            user.setRole("ROLE_JUGADOR");
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
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        if(user.isSuspend()){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return "redirect:suspended";
        }else{
            model.addAttribute("name",user.getName());
        }
        return "home";
    }

    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        if(user!= null){
            if(user.isSuspend()){
                new SecurityContextLogoutHandler().logout(request, response, authentication);
                return "redirect:suspended";
            }
        }else{
            model.addAttribute("user",false);
        }
        return "redirect:/index";
    }

    @RequestMapping(value = { "/suspended" }, method = RequestMethod.GET)
    public String suspended(HttpServletRequest request, HttpServletResponse response) {
        return "suspended";
    }

    @RequestMapping(value = { "/games/index" }, method = RequestMethod.GET)
    public String matching(Model model) {
        return "/games/index";
    }

    @RequestMapping("/" )
    public String index() {
        return "index";
    }

}
