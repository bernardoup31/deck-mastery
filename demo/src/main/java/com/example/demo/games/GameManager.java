package com.example.demo.games;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public abstract class GameManager<T extends GamePlayer> {
    protected final SimpMessagingTemplate messagingTemplate;

    public GameManager(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    public abstract void sendMessage(T player, Object message);

    public abstract void sendMessageToAll(List<T> players, Object message);

    public abstract void sendMessageToAllExcept(List<T> players, T player, Object message);

    public abstract void createGame(List<T> players);
}
