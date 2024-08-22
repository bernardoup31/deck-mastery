package com.example.demo.games;

import com.example.demo.games.Sueca.SuecaPlayer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public abstract class GameManager<T extends GamePlayer> {
    protected SimpMessagingTemplate messagingTemplate;

    public GameManager(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    public abstract void sendMessage(T player, Object message);

    public abstract void sendMessageToAll(List<T> players, Object message);
}
