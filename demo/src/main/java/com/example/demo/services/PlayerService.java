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

    public void save(Player player) {
        if (playerRepository.findByUsername(player.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (playerRepository.findByEmail(player.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        playerRepository.save(player);
    }
}
