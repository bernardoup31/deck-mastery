package com.example.demo.controller;

import com.example.demo.games.GameName;
import com.example.demo.games.queue.QueueManager;
import com.example.demo.games.sueca.manager.SuecaQueueManager;
import com.example.demo.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    public ResponseEntity<String> joinQueue(@PathVariable("gameName") GameName name, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        QueueManager queueManager = queueManagers.get(name);

        if (queueManager == null) {
            return ResponseEntity.badRequest().body("Problem finding the queue manager for the specified game.");
        }

        queueManager.addPlayerToQueue(player.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave")
    public ResponseEntity<String> leaveQueue(@PathVariable("gameName") GameName name, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        QueueManager queueManager = queueManagers.get(name);

        if (queueManager == null) {
            return ResponseEntity.badRequest().body("Problem finding the queue manager for the specified game.");
        }

        queueManager.removePlayerFromQueue(player.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept/{readyCheckId}")
    public ResponseEntity<String> acceptReadyCheck(@PathVariable("gameName") GameName name, @PathVariable("readyCheckId") UUID readyCheckId, Authentication authentication) {
        Player player = (Player) authentication.getPrincipal();
        QueueManager queueManager = queueManagers.get(name);

        if (queueManager == null) {
            return ResponseEntity.badRequest().body("Problem finding the queue manager for the specified game.");
        }

        if (!queueManager.getReadyCheckList().containsKey(readyCheckId)) {
            return ResponseEntity.badRequest().body("ID of the ready check not found.");
        }

        if (!queueManager.acceptReadyCheck(readyCheckId, player.getId())) {
            return ResponseEntity.badRequest().body("Could not complete this request. Either the ready check has expired or the player is not part of this ready check.");
        }

        return ResponseEntity.ok().build();
    }
}
