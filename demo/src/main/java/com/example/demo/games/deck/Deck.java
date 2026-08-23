package com.example.demo.games.deck;

import com.example.demo.games.GamePlayer;
import com.example.demo.games.card.Card;
import com.example.demo.games.card.Suit;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public abstract class Deck {

    protected final List<Card> cardList = new ArrayList<>();
    protected final Map<Suit, Integer> availableSuits = new HashMap<>();

    public void shuffle() {
        Collections.shuffle(cardList);
    }

    public void add(Card card) {
        cardList.add(card);

        Suit suit = card.suit();
        availableSuits.put(
                suit,
                availableSuits.getOrDefault(suit, 0) + 1
        );
    }

    public void remove(Card card) {
        if (cardList.remove(card)) {
            removeSuit(card.suit());
        }
    }

    private Card removeTopCard() {
        Card card = cardList.removeFirst();
        removeSuit(card.suit());
        return card;
    }

    private void removeSuit(Suit suit) {
        int count = availableSuits.getOrDefault(suit, 0);

        if (count <= 0) {
            throw new IllegalStateException(
                    "No cards of suit " + suit + " available"
            );
        }

        if (count == 1) {
            availableSuits.remove(suit);
        } else {
            availableSuits.put(suit, count - 1);
        }
    }

    public void clear() {
        cardList.clear();
        availableSuits.clear();
    }

    public void dealCard(GamePlayer player) {
        if (!cardList.isEmpty()) {
            player.receiveCard(removeTopCard());
        }
    }

    public Optional<Card> topCard() {
        if (cardList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(cardList.getFirst());
    }

    public Optional<Card> bottomCard() {
        if (cardList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(cardList.getLast());
    }
}
