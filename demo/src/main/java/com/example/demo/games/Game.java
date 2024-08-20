package com.example.demo.games;

import com.example.demo.games.deck.Deck;

public interface Game {

    void initiateValues();

    default void shuffle(Deck deck){
        deck.shuffle();
    }

    void distributeCards();

    void showResults();

    void saveResults();
}
