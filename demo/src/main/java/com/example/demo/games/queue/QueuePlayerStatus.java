package com.example.demo.games.queue;

// Quando um jogo é encontrado, todos precisam de aceitar (Passam do estado WAITING para READY_CHECK).
// Quando um jogador aceita, passa para o estado ACCEPTED. Quando todos aceitarem, passam para o estado STARTING.
// Se algum rejeitar ou não aceitar a tempo, voltam para o estado WAITING aqueles que estavam em ACCEPTED.
// O que não aceitou, é removido da fila de espera.
public enum QueuePlayerStatus {
    WAITING,
    READY_CHECK,
    ACCEPTED,
    STARTING
}
