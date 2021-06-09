package com.provider.internet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site/home")
public class HomeController {

    @GetMapping
    public String viewHome() {
        return "index";
    }
}
