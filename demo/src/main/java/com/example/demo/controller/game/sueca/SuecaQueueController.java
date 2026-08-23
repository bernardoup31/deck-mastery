package com.example.demo.controller.game.sueca;

import com.example.demo.dto.queue.JoinQueueRequest;
import com.example.demo.games.sueca.SuecaPlayer;
import com.example.demo.games.sueca.manager.SuecaQueueManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class SuecaQueueController {

    private final SuecaQueueManager suecaQueueManager;

    public SuecaQueueController(SuecaQueueManager suecaQueueManager) {
        this.suecaQueueManager = suecaQueueManager;
    }

    @MessageMapping("/sueca/queue/join")
    public void joinQueue(@Payload JoinQueueRequest request) {
        suecaQueueManager.addPlayerToQueue(request.playerId());
    }
}
