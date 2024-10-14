package com.example.demo.games.Sueca;

import com.example.demo.games.Game;
import com.example.demo.games.card.Card;
import com.example.demo.games.card.CardValue;
import com.example.demo.games.deck.Deck;
import com.example.demo.games.deck.TableDeck;
import com.example.demo.model.Player;
import com.nimbusds.jose.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SuecaGame implements Game, Runnable {

    private UUID id; // id of the game, for broadcasting purposes
    private final List<SuecaPlayer> players;
    private final HashMap<CardValue,Integer> cardsValue;
    private Pair<Card, SuecaPlayer> highestCard;
    private TableDeck table;
    private SuecaDeck deck;
    private SuecaTeam team1;
    private SuecaTeam team2;
    private SuecaPlayer playerShuffling;
    private SuecaPlayer playerCutting;
    private SuecaPlayer playerDistributing;
    private SuecaManager suecaManager;

    public SuecaGame(List<SuecaPlayer> players, SuecaManager suecaManager){
        this.players = players;
        this.id = UUID.randomUUID();
        this.cardsValue = new HashMap<>();
        this.deck = new SuecaDeck();
        this.table = new TableDeck();
        this.suecaManager = suecaManager;
        initiateValues();
    }

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
        int random = (int) (Math.random() * 4);
        playerShuffling = players.get(random);
        playerCutting = getNextPlayer(getNextPlayer(playerShuffling));
        playerDistributing = getNextPlayer(playerCutting);
    }

    public void cut(int index){
        deck.cutDeck(index);
    }

    private SuecaPlayer getNextPlayer(SuecaPlayer player) throws NullPointerException{
        int index = players.indexOf(player);
        if(index == 3){
            return players.getFirst();
        }
        return players.get(index + 1);
    }

    @Override
    public void distributeCards() {
        SuecaPlayer player = playerDistributing;
        for(int i = 0; i < 9; i++){
            player.receiveCard(deck.topCard());
            deck.remove(deck.topCard());
        }
        for(int i = 0; i < 3; i++){
            player = getNextPlayer(player);
            for (int j = 0; j < 10; j++){
                player.receiveCard(deck.topCard());
                deck.remove(deck.topCard());
            }
        }
    }

    @Override
    public void showResults() {

    }

    @Override
    public void saveResults() {

    }

    @Override
    public boolean isFinished() {
        return team1.getTotalScore() >= 4 || team2.getTotalScore() >= 4;
    }

    public void attributePoints(SuecaTeam team){
        if(team.getRoundScore() == 120){
            team.setTotalScore(team.getTotalScore() + 4);
        }
        else if (team.getRoundScore() >= 91){
            team.setTotalScore(team.getTotalScore() + 2);
        }
        else if (team.getRoundScore() >= 61){
            team.setTotalScore(team.getTotalScore() + 1);
        }
    }

    @Override
    public void run() {
        try {
            while (!isFinished()){
                deck.shuffle();
                suecaManager.sendMessage(playerCutting, SuecaMessages.CUT);
                // wait for player to cut
                suecaManager.sendMessage(playerDistributing, SuecaMessages.CHOOSEUPORDOWN);

            }
            showResults();
            saveResults();
            SuecaManager.removeGame(id);
        }

        catch (Exception e){
            SuecaManager.removeGame(id);
            e.printStackTrace();
        }
    }
}
