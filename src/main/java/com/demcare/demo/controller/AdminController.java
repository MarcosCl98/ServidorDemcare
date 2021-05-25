package com.demcare.demo.controller;

import com.demcare.demo.entities.User;
import com.demcare.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @RequestMapping("/admin/list")
    public String getList(Model model){
        model.addAttribute("userList", userService.getAdminList() );
        return "/admin/list";
    }

    @RequestMapping("/admin/listgames")
    public String getListGames(Model model){
        model.addAttribute("gameList", gameService.findAll() );
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("user", user.getId() );
        return "/admin/listgames";
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


}
