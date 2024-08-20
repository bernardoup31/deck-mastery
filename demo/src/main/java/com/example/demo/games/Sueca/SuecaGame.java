package com.example.demo.games.Sueca;

import com.example.demo.games.Game;
import com.example.demo.games.card.Card;
import com.example.demo.games.card.CardValue;
import com.example.demo.games.deck.Deck;
import com.example.demo.games.deck.TableDeck;
import com.example.demo.model.Player;

import java.util.HashMap;
import java.util.List;

public class SuecaGame implements Game, Runnable {

    private List<SuecaPlayer> players;
    private HashMap<CardValue,Integer> cardsValue;
    private TableDeck table;
    private SuecaDeck deck;
    private SuecaTeam team1;
    private SuecaTeam team2;



    @Override
    public void initiateValues() {
        cardsValue.put(CardValue.ACE, 11);
        cardsValue.put(CardValue.TWO, 2);
        cardsValue.put(CardValue.THREE, 3);
        cardsValue.put(CardValue.FOUR, 4);
        cardsValue.put(CardValue.FIVE, 5);
        cardsValue.put(CardValue.SIX, 6);
        cardsValue.put(CardValue.SEVEN, 10);
        cardsValue.put(CardValue.EIGHT, 0);
        cardsValue.put(CardValue.NINE, 0);
        cardsValue.put(CardValue.TEN, 0);
        cardsValue.put(CardValue.JACK, 7);
        cardsValue.put(CardValue.QUEEN, 8);
        cardsValue.put(CardValue.KING, 9);
        team1 = new SuecaTeam(players.get(0), players.get(2));
        team2 = new SuecaTeam(players.get(1), players.get(3));
    }

    public SuecaPlayer getNextPlayer(SuecaPlayer player) throws NullPointerException{
        int index = players.indexOf(player);
        if(index == 3){
            return players.getFirst();
        }
        return players.get(index + 1);
    }

    @Override
    public void distributeCards() {

    }

    @Override
    public void showResults() {

    }

    @Override
    public void saveResults() {

    }

    @Override
    public void run() {

    }
}
