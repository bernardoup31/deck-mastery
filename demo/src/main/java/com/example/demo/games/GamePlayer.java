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

    public void receiveCard(Card card){
        hand.add(card);
    }

    public void playCard(Card card){
        hand.remove(card);
    }
}
