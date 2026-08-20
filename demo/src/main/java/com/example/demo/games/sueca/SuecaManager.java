package com.example.demo.games.sueca;

import com.example.demo.games.GameManager;
import com.example.demo.games.GameQueue;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class SuecaManager extends GameManager<SuecaPlayer> implements GameQueue<SuecaPlayer> {

    private final ConcurrentHashMap<UUID, SuecaGame> games = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<SuecaPlayer, LocalDateTime> waitList = new ConcurrentHashMap<>();

    public SuecaManager(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }
    @Override
    public void addPlayerToQueue(SuecaPlayer player) {
        waitList.put(player, LocalDateTime.now());
    }

    @Override
    public void removePlayerFromQueue(SuecaPlayer player) {
        waitList.remove(player);
    }

    @Override
    public void checkForTimeout(SuecaPlayer player) {

    }

    @Override
    public boolean isReadyToStart() {
        return false;
    }

    public void addGame(UUID id, SuecaGame game) {
        games.put(id, game);
    }

    public SuecaGame getGame(UUID id) {
        return games.get(id);
    }

    public void removeGame(UUID id) {
        games.remove(id);
    }

    @Override
    public void sendMessage(SuecaPlayer player, Object message) {
        messagingTemplate.convertAndSend("/topic/sueca/" + player.getId(), message);
    }

    @Override
    public void sendMessageToAll(List<SuecaPlayer> players, Object message) {
        for (SuecaPlayer player : players) {
            messagingTemplate.convertAndSend("/topic/sueca/" + player.getId(), message);
        }
    }

    public void sendMessageToAllExcept(List<SuecaPlayer> players, SuecaPlayer player, Object message) {
        for (SuecaPlayer p : players) {
            if (!p.equals(player)) {
                messagingTemplate.convertAndSend("/topic/sueca/" + p.getId(), message);
            }
        }
    }

    public Future<Integer> processCut(SuecaGame game, int cutIndex) {
        CompletableFuture<Integer> futureIndex = new CompletableFuture<>();
        return futureIndex;
    }
}
