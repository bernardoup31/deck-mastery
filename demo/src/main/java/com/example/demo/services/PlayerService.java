package com.example.demo.services;

import com.example.demo.db.PlayerRepository;
import com.example.demo.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository userRepository) {
        this.playerRepository = userRepository;
    }

    public Player getUser(Long id) {
        return new Player();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerRepository.findByUsername(username)
                .or(() -> playerRepository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
