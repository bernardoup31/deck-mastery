package com.example.demo.games.deck;

import com.example.demo.games.card.Card;
import com.example.demo.games.card.Suit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDeck extends Deck {

    private Suit firstSuit;

    public TableDeck(){
        super();
    }

    @Override
    public void add(Card card) {
        if (cardList.isEmpty()) {
            firstSuit = card.suit();
        }
        super.add(card);
    }
}
