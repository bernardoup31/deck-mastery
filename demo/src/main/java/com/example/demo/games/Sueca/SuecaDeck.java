package com.example.demo.games.Sueca;

import com.example.demo.games.card.Card;
import com.example.demo.games.card.CardValue;
import com.example.demo.games.card.Suit;
import com.example.demo.games.deck.Deck;

public class SuecaDeck extends Deck {

    public SuecaDeck(){
        regenerateDeck();
    }

    public void regenerateDeck(){
        for(Suit suit : Suit.values()){
            if(suit == Suit.JOKER){
                continue;
            }
            for(CardValue value : CardValue.values()){
                if(value == CardValue.EIGHT || value == CardValue.NINE || value == CardValue.TEN){
                    continue;
                }
                add(new Card(suit, value));
            }
        }
    }

    public void cutDeck(int index){
        cardList.addAll(cardList.subList(0, index));
        cardList.removeAll(cardList.subList(0, index));
    }

    public void removeCard(Card card){
        cardList.remove(card);
    }
}
