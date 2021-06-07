package com.demcare.demo.controller;


import com.demcare.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@CrossOrigin(origins = "*")
public class TokenController extends DemcareController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping("/token/{code}")
    public String loginToken(@PathVariable String code){
        securityService.logByToken(code);
        return "redirect:/home";
    }
}
