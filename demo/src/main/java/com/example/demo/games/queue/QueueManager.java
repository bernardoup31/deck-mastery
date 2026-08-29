package com.example.demo.games.queue;

import com.example.demo.dto.queue.QueueEvent;
import com.example.demo.games.GameName;
import lombok.Getter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class QueueManager {

    private final ConcurrentHashMap<Long, Instant> waitList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, ReadyCheck> readyCheckList = new ConcurrentHashMap<>();

    private final int playersNeededToStart;
    private final SimpMessagingTemplate messagingTemplate;
    private final String destinationEndpoint;

    private static final int QUEUE_TIMEOUT_SECONDS = 120;
    private static final int READY_CHECK_TIMEOUT_SECONDS = 30;

    protected QueueManager(int playersNeededToStart, String destinationEndpoint, SimpMessagingTemplate template) {
        this.playersNeededToStart = playersNeededToStart;
        this.messagingTemplate = template;
        this.destinationEndpoint = destinationEndpoint;
    }

    public abstract GameName getGameName();

    public synchronized void addPlayerToQueue(Long playerId) {
        waitList.putIfAbsent(playerId, Instant.now());
        if (isReadyToStart()) {
            startReadyCheck();
        }
    }

    public synchronized void removePlayerFromQueue(Long playerId) {
        waitList.remove(playerId);
    }

    private boolean isReadyToStart() {
        return waitList.size() >= playersNeededToStart;
    }

    private void startReadyCheck() {
        List<Long> players = waitList.entrySet()
                .stream()
                .limit(playersNeededToStart)
                .map(Map.Entry::getKey)
                .toList();

        if (players.size() < playersNeededToStart) {
            return;
        }

        ReadyCheck readyCheck = new ReadyCheck(players);
        readyCheckList.put(readyCheck.getId(), readyCheck);

        for  (Long playerId : players) {
            waitList.remove(playerId);
        }

        notifyMatchFound(players);
    }

    public synchronized boolean acceptReadyCheck(UUID readyCheckId, Long playerId) {
        ReadyCheck readyCheck = readyCheckList.get(readyCheckId);
        if (readyCheck == null) {
            return false;
        }

        if (!readyCheck.getPlayerIds().contains(playerId)) {
            return false;
        }

        readyCheck.getAcceptedPlayers().add(playerId);

        if (readyCheck.getAcceptedPlayers().size() == playersNeededToStart) {
            readyCheckList.remove(readyCheckId);
            notifyMatchIsStarting(readyCheck.getPlayerIds());
        }

        return true;
    }

    @Scheduled(fixedRate = 5000)
    public synchronized void checkQueueTimeouts(){
        Instant now = Instant.now();

        List<Long> timedOutPlayers = waitList.entrySet()
                .stream()
                .filter(entry ->
                                now.isAfter(entry.getValue().plusSeconds(QUEUE_TIMEOUT_SECONDS)))
                .map(Map.Entry::getKey)
                .toList();

        if (!timedOutPlayers.isEmpty()) {
            for (Long playerId : timedOutPlayers) {
                waitList.remove(playerId);
            }
            notifyMatchCancelledAndRemovedFromQueue(timedOutPlayers, "Match cancelled due to timeout on queue.");
        }
    }

    @Scheduled(fixedRate = 1000)
    public synchronized void checkReadyCheckTimeout(){
        Instant now = Instant.now();

        List<ReadyCheck> timedOutRooms = readyCheckList.values()
                .stream()
                .filter(readyCheck -> now.isAfter(readyCheck.getStartedAt().plusSeconds(READY_CHECK_TIMEOUT_SECONDS)))
                .toList();

        for (ReadyCheck readyCheck : timedOutRooms) {
            List<Long> playersToRemoveFromQueue = new ArrayList<>();
            List<Long> playersToReturnToQueue = new ArrayList<>();
            readyCheckList.remove(readyCheck.getId());

            List<Long> playerIds = readyCheck.getPlayerIds();
            for (Long playerId : playerIds) {
                if (readyCheck.getAcceptedPlayers().contains(playerId)) {
                    playersToReturnToQueue.add(playerId);
                    waitList.put(playerId, Instant.now());
                } else {
                    playersToRemoveFromQueue.add(playerId);
                }
            }

            notifyMatchCancelledAndRemovedFromQueue(playersToRemoveFromQueue, "Match not accepted on time. You have been removed from the queue.");
            notifyMatchCancelledAndReturnedToQueue(playersToReturnToQueue, "Match cancelled due to one of the players not accepting the match on time.");
        }
    }

    protected void notifyMatchFound(List<Long> playerIds) {
        notifyPlayers(playerIds, QueueEventType.MATCH_FOUND);
    }

    protected void notifyMatchCancelledAndReturnedToQueue(List<Long> playerIds, String reason) {
        notifyPlayers(playerIds, QueueEventType.MATCH_CANCELLED_AND_RETURNED_TO_QUEUE, reason);
    }

    protected void notifyMatchCancelledAndRemovedFromQueue(List<Long> playerIds, String reason) {
        notifyPlayers(playerIds, QueueEventType.MATCH_CANCELLED_AND_REMOVED_FROM_QUEUE, reason);
    }

    protected void notifyMatchIsStarting(List<Long> playerIds) {
        notifyPlayers(playerIds, QueueEventType.MATCH_STARTING);
    }

    private void notifyPlayers(List<Long> playerIds, QueueEventType eventType) {
        notifyPlayers(playerIds, eventType, "");
    }

    private void notifyPlayers(List<Long> playerIds, QueueEventType eventType, String message) {
        QueueEvent queueEvent = new QueueEvent(eventType, message);

        for (Long playerId : playerIds) {
            messagingTemplate.convertAndSendToUser(
                    playerId.toString(),
                    destinationEndpoint,
                    queueEvent
            );
        }
    }
}
