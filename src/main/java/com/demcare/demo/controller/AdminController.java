package com.demcare.demo.controller;

import com.demcare.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminController extends DemcareController {

    @Autowired
    private UserService userService;


    @RequestMapping("/admin/list")
    public String getList(Model model){
        model.addAttribute("userList", userService.getAdminList() );
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


}
