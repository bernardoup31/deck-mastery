package com.example.demo.games;

public interface GameQueue<T extends GamePlayer> {

    void addPlayerToQueue(T player);

    void removePlayerFromQueue(T player);

    void checkForTimeout(T player);

    boolean isReadyToStart();
}
