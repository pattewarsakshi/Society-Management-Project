package com.society.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping({
        "/",
        "/login",
        "/register",
        "/forgot-password",
        "/reset-password",

        "/admin/**",
        "/guard/**",
        "/member/**",

        "/notices/**",
        "/complaints/**",
        "/amenities/**",
        "/documents/**",
        "/parking/**",
        "/profile",
        "/notifications"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
