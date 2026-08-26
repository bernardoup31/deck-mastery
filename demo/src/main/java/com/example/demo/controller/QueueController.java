package com.example.demo.controller;

import com.example.demo.games.GameName;
import com.example.demo.games.queue.QueueManager;
import com.example.demo.games.sueca.manager.SuecaQueueManager;
import com.example.demo.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{gameName}/queue")
public class QueueController {

    private final Map<GameName, QueueManager> queueManagers;

    public QueueController(List<QueueManager> managers) {
        this.queueManagers = managers.stream()
                .collect(Collectors.toMap(
                        QueueManager::getGameName,
                        manager -> manager
                ));
    }

    @PostMapping("/join")
    public HttpStatus joinQueue(@PathVariable("gameName") GameName name, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        QueueManager queueManager = queueManagers.get(name);

        if (queueManager == null) {
            return HttpStatus.BAD_REQUEST;
        }

        queueManager.addPlayerToQueue(player.getId());
        return HttpStatus.OK;
    }

    @PostMapping("/leave")
    public HttpStatus leaveQueue(@PathVariable("gameName") GameName name, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        QueueManager queueManager = queueManagers.get(name);

        if (queueManager == null) {
            return HttpStatus.BAD_REQUEST;
        }

        queueManager.removePlayerFromQueue(player.getId());
        return HttpStatus.OK;
    }
}
