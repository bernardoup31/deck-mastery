package com.example.demo.controller.game;

import com.example.demo.games.sueca.SuecaGame;
import com.example.demo.games.sueca.SuecaManager;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class SuecaController {

    SuecaManager suecaManager;

    public SuecaController(SuecaManager suecaManager){
        this.suecaManager = suecaManager;
    }

    @MessageMapping("/sueca/{gameID}/cut")
    public void cutDeck(@DestinationVariable UUID gameID, @Payload int cutIndex){
        SuecaGame game = suecaManager.getGame(gameID);
        game.cut(cutIndex);
    }
}
