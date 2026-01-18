package com.zhengyang.backend.home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    
    // public endpoint
    @GetMapping
    public String index() {
        return "Welcome to the Finance Tracker API";
    }
}
