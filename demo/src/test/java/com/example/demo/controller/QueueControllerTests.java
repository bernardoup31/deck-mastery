package com.example.demo.controller;

import com.example.demo.games.GameName;
import com.example.demo.games.queue.QueueManager;
import com.example.demo.games.sueca.manager.SuecaQueueManager;
import com.example.demo.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

public class QueueControllerTests {

    private QueueController queueController;
    private Authentication authentication;
    private SuecaQueueManager suecaQueueManager;

    @BeforeEach
    public void setUp() {
        suecaQueueManager = new SuecaQueueManager(Mockito.mock(SimpMessagingTemplate.class));

        List<QueueManager> managers = new ArrayList<>();
        managers.add(suecaQueueManager);

        queueController = new QueueController(managers);

        authentication = Mockito.mock(Authentication.class);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getId()).thenReturn(1L);

        Mockito.when(authentication.getPrincipal()).thenReturn(player);
    }

    @Test
    public void testJoinQueue() {
        Assertions.assertEquals(0, suecaQueueManager.getWaitList().size());
        queueController.joinQueue(GameName.SUECA, authentication);
        Assertions.assertEquals(1, suecaQueueManager.getWaitList().size());
    }

    @Test
    public void testLeaveQueue() {
        queueController.joinQueue(GameName.SUECA, authentication);
        Assertions.assertEquals(1, suecaQueueManager.getWaitList().size());
        queueController.leaveQueue(GameName.SUECA, authentication);
        Assertions.assertEquals(0, suecaQueueManager.getWaitList().size());
    }
}
