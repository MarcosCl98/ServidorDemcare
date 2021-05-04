package com.demcare.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends DemcareController {

    @RequestMapping("/" )
    public String index() {
        return "index";
    }

}
