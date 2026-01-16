package com.zhengyang.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Welcome to the home page! This is accessible to everyone.";
    }
    
    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint accessible to all users.";
    }
}
