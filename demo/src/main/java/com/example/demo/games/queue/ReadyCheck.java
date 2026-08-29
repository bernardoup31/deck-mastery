package com.example.demo.games.queue;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ReadyCheck {

    private final UUID id; // id of the future possible match
    private final Instant startedAt;
    private final List<Long> playerIds;
    private final Set<Long> acceptedPlayers;

    public ReadyCheck(List<Long> playerIds) {
        this.id = UUID.randomUUID();
        this.startedAt = Instant.now();
        this.playerIds = playerIds;
        this.acceptedPlayers = new HashSet<>();
    }
}
