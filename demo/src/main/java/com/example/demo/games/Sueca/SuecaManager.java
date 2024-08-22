package com.example.demo.games.Sueca;

import com.example.demo.games.GameManager;
import com.example.demo.games.GamePlayer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuecaManager extends GameManager<SuecaPlayer> {

    public SuecaManager(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    @Override
    public void sendMessage(SuecaPlayer player, Object message) {
        messagingTemplate.convertAndSend("/topic/sueca/" + player.getId(), message);
    }

    @Override
    public void sendMessageToAll(List<SuecaPlayer> players, Object message) {
        for (SuecaPlayer player : players) {
            messagingTemplate.convertAndSend("/topic/sueca/" + player.getId(), message);
        }
    }
}
