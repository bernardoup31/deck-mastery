package com.example.demo.games.queue;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class QueueEntry {

    private final Instant joinedAt;
    private Instant readyCheckStartedAt;
    private QueuePlayerStatus status;

    public QueueEntry() {
        this.joinedAt = Instant.now();
        this.status = QueuePlayerStatus.WAITING;
    }
}
