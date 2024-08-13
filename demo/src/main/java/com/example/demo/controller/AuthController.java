package com.example.demo.controller;


import com.example.demo.controller.utils.ControllerUtils;
import com.example.demo.model.Role;
import com.example.demo.model.auth.AuthenticationRequest;
import com.example.demo.model.Player;
import com.example.demo.model.auth.RegisterRequest;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.controller.utils.ControllerUtils.redirect;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PlayerService playerService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login") // Para segurança, mudar a forma como é enviada a password, para evitar ataques man in the middle (enviar já encriptada)
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        final UserDetails user = playerService.loadUserByUsername(request.getEmail());
        if(user!=null){
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String passwordEncoded = new BCryptPasswordEncoder().encode(request.getPassword());
        Player newPlayer = new Player(request.getUsername(), request.getEmail(), passwordEncoded, Role.USER);
        try{
            playerService.save(newPlayer);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(redirect("/"), HttpStatus.FOUND);
    }
}
