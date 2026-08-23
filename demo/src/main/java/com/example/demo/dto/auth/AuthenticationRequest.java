package com.example.demo.dto.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record AuthenticationRequest(
        String email,
        String password
) {}
