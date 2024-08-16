package com.example.demo.model;

import com.example.demo.model.card.CardValue;
import lombok.Getter;

import java.util.Map;

public abstract class Game {
    @Getter
    private Map<CardValue,Integer> cardValues;
    private Deck deck;

    public Game(){
        initiateValues();
    }

    public abstract void initiateValues();

    public void shuffle(){

    }
}
