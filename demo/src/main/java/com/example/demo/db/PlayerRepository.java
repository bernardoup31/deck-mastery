package com.example.demo.db;

import com.example.demo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {


    Optional<Player> findUserByEmail(String email);
}
