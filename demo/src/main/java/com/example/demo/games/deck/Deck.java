package com.example.demo.games.deck;

import com.example.demo.games.card.Card;
import com.example.demo.games.card.Suit;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public abstract class Deck {

    protected List<Card> cardList = new ArrayList<>();
    protected HashMap<Suit, Integer> availableSuits = new HashMap<>();

    public void shuffle(){
        Collections.shuffle(cardList);
    }

    public void add(Card card){
        cardList.add(card);
        Suit suit = card.suit();
        availableSuits.put(suit, availableSuits.getOrDefault(suit, 0) + 1);
    }

    public void remove(Card card){
        cardList.remove(card);
        removeSuit(card.suit());
    }

    //Para que seja mais rápido, não é necessário procurar a carta na lista
    public void removeTopCard(){
        Card card = cardList.getFirst();
        cardList.removeFirst();
        removeSuit(card.suit());
    }

    private void removeSuit(Suit suit) {
        assert availableSuits.containsKey(suit);
        assert availableSuits.get(suit) > 0;
        availableSuits.put(suit, availableSuits.get(suit) - 1);
    }

    public void clear(){
        cardList.clear();
        availableSuits.clear();
    }

    public Card topCard(){
        return cardList.getLast();
    }

    public Card bottomCard(){
        return cardList.getFirst();
    }
}
