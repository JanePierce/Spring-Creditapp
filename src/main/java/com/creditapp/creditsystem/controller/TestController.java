package com.creditapp.creditsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/Brodyaga")
    public String test() {
        System.out.println("Бродяга любит пиво");
        return "Page";
    }
}
