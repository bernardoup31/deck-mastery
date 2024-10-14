package com.example.demo.games.Sueca;

import com.example.demo.games.GameManager;
import com.example.demo.games.GamePlayer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Service
public class SuecaManager extends GameManager<SuecaPlayer> {

    private static final ConcurrentHashMap<UUID, SuecaGame> games = new ConcurrentHashMap<>();

    public SuecaManager(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    public static void addGame(UUID id, SuecaGame game) {
        games.put(id, game);
    }

    public static SuecaGame getGame(UUID id) {
        return games.get(id);
    }

    public static void removeGame(UUID id) {
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
