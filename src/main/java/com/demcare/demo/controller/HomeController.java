package com.demcare.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
public class HomeController extends DemcareController {

    @RequestMapping("/" )
    public String index() {
        return "index";
    }

}
