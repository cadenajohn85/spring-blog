package com.codeup.blog.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String rollDice() { return "roll-dice"; }

    @GetMapping("/roll-dice/{number}")
    public String guessNumber(@PathVariable int number, Model vModel) {
        vModel.addAttribute("guess", number);
        vModel.addAttribute("guessMsg", "You guessed: " + number);
        int randomRoll = (int) Math.floor(Math.random() * 6 + 1);
        System.out.println("randomRoll = " + randomRoll);
        vModel.addAttribute("roll", randomRoll);
        vModel.addAttribute("rollMsg", "The roll was: " + randomRoll);
        
        return "roll-dice";
    }

}
