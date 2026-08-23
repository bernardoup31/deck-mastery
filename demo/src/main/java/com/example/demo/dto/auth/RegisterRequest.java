package com.example.demo.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record RegisterRequest(
        String username,
        String email,
        String password
) {}
