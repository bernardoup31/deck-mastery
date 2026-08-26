package com.example.demo.games.sueca.manager;

import com.example.demo.dto.queue.QueueEvent;
import com.example.demo.games.GameName;
import com.example.demo.games.queue.QueueEventType;
import com.example.demo.games.queue.QueueManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuecaQueueManager extends QueueManager {

    public SuecaQueueManager(SimpMessagingTemplate template) {
        super(4, "/sueca/queue", template);
    }

    @Override
    public GameName getGameName() {
        return GameName.SUECA;
    }
}
