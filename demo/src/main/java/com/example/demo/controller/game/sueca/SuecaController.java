package com.example.demo.controller.game.sueca;

import com.example.demo.games.sueca.SuecaGame;
import com.example.demo.games.sueca.manager.SuecaGameManager;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class SuecaController {

    SuecaGameManager suecaGameManager;

    public SuecaController(SuecaGameManager suecaGameManager){
        this.suecaGameManager = suecaGameManager;
    }

    @MessageMapping("/sueca/{gameID}/cut")
    public void cutDeck(@DestinationVariable UUID gameID, @Payload int cutIndex){
        SuecaGame game = suecaGameManager.getGame(gameID);
        game.cut(cutIndex);
    }
}
