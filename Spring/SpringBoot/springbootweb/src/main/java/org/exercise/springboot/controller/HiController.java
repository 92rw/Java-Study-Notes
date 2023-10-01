package org.exercise.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @RequestMapping("/1.jpg")
    public String hi() {
        return "Enjoy your cakes \uD83C\uDF70 \uD83C\uDF82 \uD83C\uDF6F";
    }
}
