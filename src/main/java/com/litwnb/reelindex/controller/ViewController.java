package com.litwnb.reelindex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping({"/", "/movies"})
    public String movies() {
        return "forward:/index.html";
    }

    @GetMapping("/movie")
    public String movie() {
        return "forward:/movie.html";
    }

    @GetMapping("/ratings")
    public String ratings() {
        return "forward:/ratings.html";
    }

    @GetMapping("/watchlist")
    public String watchlist() {
        return "forward:/watchlist.html";
    }

    @GetMapping("/user")
    public String user() {
        return "forward:/user.html";
    }

    @GetMapping("/change-password")
    public String changePassword() {
        return "forward:/change-password.html";
    }

    @GetMapping("/register")
    public String register() {
        return "forward:/register.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }
}
