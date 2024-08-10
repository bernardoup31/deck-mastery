package com.example.demo.services;

import com.example.demo.db.PlayerRepository;
import com.example.demo.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository userRepository) {
        this.playerRepository = userRepository;
    }

    public Player getUser() {
        return new Player();
    }
}
