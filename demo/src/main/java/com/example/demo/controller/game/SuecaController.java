package com.example.demo.controller.game;

import com.example.demo.games.Sueca.SuecaGame;
import com.example.demo.games.Sueca.SuecaManager;
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
    public void cutDeck(@DestinationVariable UUID gameId, @Payload int cutIndex){
        SuecaGame game = SuecaManager.getGame(gameId);
    }
}
