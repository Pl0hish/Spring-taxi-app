package com.mnyshenko.taxiSpringApp.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }
}
