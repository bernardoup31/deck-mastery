package com.example.demo.games.sueca.manager;

import com.example.demo.games.QueueManager;
import com.example.demo.games.sueca.SuecaPlayer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SuecaQueueManager extends QueueManager {

    public SuecaQueueManager(SimpMessagingTemplate template) {
        super(4, template);
    }

    @Override
    protected void notifyMatchFound(List<Long> playerIds) {

    }

    @Override
    protected void notifyMatchCancelled(List<Long> playerIds) {

    }

    @Override
    protected void notifyMatchIsStarting(List<Long> playerIds) {

    }
}
