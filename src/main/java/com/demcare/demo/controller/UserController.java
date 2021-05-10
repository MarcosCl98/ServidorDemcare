package com.demcare.demo.controller;

import com.demcare.demo.entities.User;
import com.demcare.demo.service.RolesService;
import com.demcare.demo.validators.LoginFormValidator;
import com.demcare.demo.validators.SingUpFormValidator;
import com.demcare.demo.service.SecurityService;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService uderDetailsService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SingUpFormValidator signUpFormValidator;

    @Autowired
    private LoginFormValidator loginFormValidator;

    @Autowired
    private HttpSession httpSession;

    @RequestMapping(value="/user/add", method=RequestMethod.POST )
    public String setUser(@Validated User user, BindingResult result, Model
            model) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "singup";
        }
        model.addAttribute("rolesList", rolesService.getRoles());
        userService.register(user);
        return "redirect:/home";
    }

    @RequestMapping(value="/user/add")
    public String getUser(Model model){
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("user", new User());
        return "user/add";
    }



    //TODO: No funciona del todo el actualizar
    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("userList", userService.getUsersList() );
        return "user/list :: tableUsers";
    }

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
        securityService.autoLogin(user.getMail(), user.getPasswordConfirm());
        return "redirect:home";
    }

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Validated User user, BindingResult result, Model
            model) {

        *//*loginFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "login";
        }*//*
        return "redirect:home";
    }*/

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }



    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
    /*    SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        List<User> users = userService.getNotAsociatedUsers();*/
        return "home";
    }

    @RequestMapping("/admin/list")
    public String getList(Model model){
        model.addAttribute("userList", userService.getUsersList() );
        return "/admin/list";
    }

    @RequestMapping("/admin/delete/{id}" )
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/admin/list";
    }

    @RequestMapping("/admin/suspend/{id}" )
    public String suspendUser(@PathVariable Long id){
        userService.suspendUser(id);
        return "redirect:/admin/list";
    }

    @RequestMapping("/admin/activate/{id}" )
    public String activateUser(@PathVariable Long id){
        userService.activateUser(id);
        return "redirect:/admin/list";
    }

    @RequestMapping("/institution/listUsers")
    public String getInstitutionList(Model model){
        model.addAttribute("userList", userService.getNotAsociatedUsers());
        return "/institution/listUsers";
    }

    @RequestMapping("/institution/asociate/{id}" )
    public String asociateUser(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        userService.asociateUser(user.getId(),id);
        return "redirect:/institution/listUsers";
    }

}
