package com.example.demo.games.queue;

import com.example.demo.dto.queue.QueueEvent;
import com.example.demo.games.GameName;
import lombok.Getter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class QueueManager {

    private final int playersNeededToStart;
    private final ConcurrentHashMap<Long, QueueEntry> waitList = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;
    private final String destinationEndpoint;

    protected QueueManager(int playersNeededToStart, String destinationEndpoint, SimpMessagingTemplate template) {
        this.playersNeededToStart = playersNeededToStart;
        this.messagingTemplate = template;
        this.destinationEndpoint = destinationEndpoint;
    }

    public abstract GameName getGameName();

    public synchronized void addPlayerToQueue(Long playerId) {
        waitList.putIfAbsent(playerId, new QueueEntry());
        if (isReadyToStart()) {
            startReadyCheck();
        }
    }

    public synchronized void removePlayerFromQueue(Long playerId) {
        waitList.remove(playerId);
    }

    public boolean isReadyToStart() {
        return waitList.values().stream()
                .filter(entry ->
                        entry.getStatus() == QueuePlayerStatus.WAITING)
                .count() >= playersNeededToStart;
    }

    private void startReadyCheck() {
        List<Long> players = waitList.entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue().getStatus() == QueuePlayerStatus.WAITING)
                .limit(playersNeededToStart)
                .map(Map.Entry::getKey)
                .toList();

        if (players.size() < playersNeededToStart) {
            return;
        }

        Instant now = Instant.now();

        for (Long playerId : players) {
            QueueEntry entry = waitList.get(playerId);
            if (entry != null) {
                entry.setStatus(QueuePlayerStatus.READY_CHECK);
                entry.setReadyCheckStartedAt(now);
            }
        }

        notifyMatchFound(players);
    }

    protected void notifyMatchFound(List<Long> playerIds) {
        notifyPlayers(playerIds, QueueEventType.MATCH_FOUND);
    }

    protected void notifyMatchCancelled(List<Long> playerIds) {
        notifyPlayers(playerIds, QueueEventType.MATCH_CANCELLED);
    }

    protected void notifyMatchIsStarting(List<Long> playerIds) {
        notifyPlayers(playerIds, QueueEventType.MATCH_STARTING);
    }

    private void notifyPlayers(List<Long> playerIds, QueueEventType eventType) {
        QueueEvent queueEvent = new QueueEvent(eventType);

        for (Long playerId : playerIds) {
            messagingTemplate.convertAndSendToUser(
                    playerId.toString(),
                    destinationEndpoint,
                    queueEvent
            );
        }
    }
}
