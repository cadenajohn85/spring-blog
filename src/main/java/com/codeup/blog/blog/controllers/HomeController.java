package com.codeup.blog.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

//    Create a HomeController class. This class should have one method with a GetMapping for /. It should return a string that says "This is the landing page!".

    @GetMapping("/")
    @ResponseBody
    public String displayLandingPage() {
        return "This is the landing page!";
    }

}
