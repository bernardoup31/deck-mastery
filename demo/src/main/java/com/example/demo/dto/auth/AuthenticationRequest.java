package com.example.demo.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record AuthenticationRequest(
        @Email
        String email,

        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {}
