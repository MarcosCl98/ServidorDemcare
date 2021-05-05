package com.demcare.demo.controller;

import com.demcare.demo.entities.User;
import com.demcare.demo.validators.SingUpFormValidator;
import com.demcare.demo.service.SecurityService;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SingUpFormValidator signUpFormValidator;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@ModelAttribute User user){

        validateRequiredParam(user.getMail(), "mail");
        validateRequiredParam(user.getPassword(), "password");
        validateRequiredParam(user.getName(), "name");
        validateRequiredParam(user.getSurname(), "surname");

        User userModelRegistred = userService.register(user);
        return new ResponseEntity<>(userModelRegistred, HttpStatus.OK);
    }

    @RequestMapping(value="/user/add", method=RequestMethod.POST )
    public String setUser(@RequestParam String nombre,
                          @RequestParam String apellido1,
                          @RequestParam String apellido2){
        return "redirect:/";
    }

    @RequestMapping("/user/add" )
    public String getUser(Model model){
        model.addAttribute("user", new User());
       //TODO: error
        return "/user/add";
    }

    @RequestMapping("/user/list")
    public String getList(Model model){
        model.addAttribute("userList", userService.getUsers() );
        return "/user/list";
    }

    //TODO: No funciona del todo el actualizar
    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("userList", userService.getUsers() );
        return "user/list :: tableUsers";
    }

    @RequestMapping(value = "/singup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "singup";
    }

    @RequestMapping(value = "/singup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result, Model
            model) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "singup";
        }
        user.setRole("ROLE_PRUEBA");
        userService.register(user);
        securityService.autoLogin(user.getMail(), user.getPasswordConfirm());
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

}
