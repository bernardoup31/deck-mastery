package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService userService) {
        this.playerService = userService;
    }
    @GetMapping("{id}")
    public Player getUser(@PathVariable Long id) {
        return playerService.getUser(id);
    }
}
