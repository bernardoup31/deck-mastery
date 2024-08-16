package com.example.demo.model.card;

import lombok.Getter;

@Getter
public class Card {
    private final String value;
    private final Suit suit;

    public Card(String value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
