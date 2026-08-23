package com.example.demo.games.sueca.manager;

import com.example.demo.games.GameManager;
import com.example.demo.games.sueca.SuecaGame;
import com.example.demo.games.sueca.SuecaPlayer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class SuecaGameManager extends GameManager<SuecaPlayer> {

    private final ConcurrentHashMap<UUID, SuecaGame> games = new ConcurrentHashMap<>();

    public SuecaGameManager(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    public void addGame(SuecaGame game) {
        games.put(game.getId(), game);
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

    @Override
    public void sendMessageToAllExcept(List<SuecaPlayer> players, SuecaPlayer player, Object message) {
        for (SuecaPlayer p : players) {
            if (!p.equals(player)) {
                messagingTemplate.convertAndSend("/topic/sueca/" + p.getId(), message);
            }
        }
    }

    @Override
    public void createGame(List<SuecaPlayer> players) {
        SuecaGame game = new SuecaGame(players);
        addGame(game);

    }
}
