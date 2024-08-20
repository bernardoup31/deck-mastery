package com.example.demo.games.Sueca;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuecaTeam {
    private final SuecaPlayer player1;
    private final SuecaPlayer player2;
    private int totalScore;
    private int roundScore;

    public SuecaTeam(SuecaPlayer player1, SuecaPlayer player2){
        this.player1 = player1;
        this.player2 = player2;
        this.totalScore = 0;
        this.roundScore = 0;
    }
}
