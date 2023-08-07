package com.portfolio.communityuser.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class UserController {

    @GetMapping
    public String hello() {
        return "hello";
    }
}
