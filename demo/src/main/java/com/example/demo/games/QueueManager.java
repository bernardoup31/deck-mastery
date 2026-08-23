package com.example.demo.games;

import com.example.demo.games.sueca.SuecaPlayer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public abstract class QueueManager {

    private final int playersNeededToStart;
    private final ConcurrentHashMap<Long, Instant> waitList = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    protected QueueManager(int playersNeededToStart, SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.playersNeededToStart = playersNeededToStart;
    }

    public void addPlayerToQueue(Long playerId) {
        waitList.put(playerId, Instant.now());
    }

    public void removePlayerFromQueue(Long playerId) {
        waitList.remove(playerId);
    }

    public boolean isReadyToStart() {
        return waitList.size() >= playersNeededToStart;
    }

    protected abstract void notifyMatchFound(List<Long> playerIds);

    protected abstract void notifyMatchCancelled(List<Long> playerIds);

    protected abstract void notifyMatchIsStarting(List<Long> playerIds);
}
