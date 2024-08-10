package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService userService) {
        this.playerService = userService;
    }
    @GetMapping()
    public Player getUser() {
        return playerService.getUser();
    }
}
