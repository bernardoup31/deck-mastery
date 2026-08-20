package com.example.demo.games;

import com.example.demo.games.card.Card;
import com.example.demo.games.deck.Deck;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GamePlayer {
    private final Long id;
    private Deck hand;

    public GamePlayer(Long id, Deck hand){
        this.id = id;
        this.hand = hand;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GamePlayer other) {
            return this.id.equals(other.id);
        } else {
            return false;
        }
    }

    public void receiveCard(Card card){
        hand.add(card);
    }

    public void playCard(Card card){
        hand.remove(card);
    }
}
