package com.codeup.blog.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {

//    This controller should listen for requests for several routes that correspond to basic arithmetic operations and produce the result of the arithmetic.

//    Example
//
//    url	response
///add/3/and/4	7
//            /subtract/3/from/10	7
//            /multiply/4/and/5	20
//            /divide/6/by/3	2

    @GetMapping("/add/{num1}/and/{num2}")
    @ResponseBody
    public String add(@PathVariable int num1, @PathVariable int num2) {
        return num1 + " + " + num2 + " = " + (num1 + num2) + ".";
    }

    @GetMapping("/subtract/{num1}/and/{num2}")
    @ResponseBody
    public String subtract(@PathVariable int num1, @PathVariable int num2) {
        return num1 + " - " + num2 + " = " + (num1 - num2) + ".";
    }

    @GetMapping("/multiply/{num1}/and/{num2}")
    @ResponseBody
    public String multiply(@PathVariable int num1, @PathVariable int num2) {
        return num1 + " * " + num2 + " = " + (num1 * num2) + ".";
    }

    @GetMapping("/divide/{num1}/and/{num2}")
    @ResponseBody
    public String divide(@PathVariable int num1, @PathVariable int num2) {
        return num1 + " / " + num2 + " = (a floored int of) " + (num1 / num2) + ".";
    }
}
