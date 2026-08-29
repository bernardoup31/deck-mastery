package com.example.demo.dto.queue;

import com.example.demo.games.queue.QueueEventType;
import jakarta.validation.constraints.NotBlank;

public record QueueEvent(@NotBlank QueueEventType eventType, String message) {
}
