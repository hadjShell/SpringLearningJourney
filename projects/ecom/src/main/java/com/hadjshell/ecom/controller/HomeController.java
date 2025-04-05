package com.hadjshell.ecom.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@SuppressWarnings("unused")
public class HomeController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
