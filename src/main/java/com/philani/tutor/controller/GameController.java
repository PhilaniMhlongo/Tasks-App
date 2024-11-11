package com.philani.tutor.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.philani.tutor.model.GameProgressUpdate;
import com.philani.tutor.model.GameSession;
import com.philani.tutor.model.GameSessionRequest;
import com.philani.tutor.repository.GameRepository;

@RestController
@RequestMapping("/api/game")
public class GameController {
    
    private final GameRepository gameRepository;
    
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    
    @GetMapping("/library/{complexity}")
    public ResponseEntity<List<String>> getWordLibrary(@PathVariable int complexity) {
        return ResponseEntity.ok(gameRepository.getWordLibrary(complexity));
    }
    
    @PostMapping("/session/start")
    public ResponseEntity<GameSession> startSession(@RequestBody GameSessionRequest request) {
        return ResponseEntity.ok(gameRepository.createSession(request.getLevel()));
    }
    
    @PutMapping("/session/{sessionId}")
    public ResponseEntity<GameSession> updateSession(
            @PathVariable long sessionId,
            @RequestBody GameProgressUpdate update) {
        
        GameSession session = gameRepository.getSession(sessionId);
        updateSessionFromProgress(session, update);
        gameRepository.updateSession(session);
        
        return ResponseEntity.ok(session);
    }
    
    @GetMapping("/highscores")
    public ResponseEntity<List<GameSession>> getHighScores(
            @RequestParam(required = false) Integer level) {
        return ResponseEntity.ok(gameRepository.getTopScores(level, 10));
    }
    
    private void updateSessionFromProgress(GameSession session, GameProgressUpdate update) {
        session.setKillCount(update.getKillCount());
        session.setHitCount(update.getHitCount());
        session.setFireCount(update.getFireCount());
        session.setAccuracy(calculateAccuracy(update.getHitCount(), update.getFireCount()));
        session.setPassedCount(update.getPassedCount());
        session.getWeapons().putAll(update.getWeapons());
        session.getProblematicSequences().putAll(update.getProblematicSequences());
        
        if (update.isGameOver()) {
            session.setEndTime(LocalDateTime.now());
            session.setElapsedTimeSeconds(
                ChronoUnit.SECONDS.between(session.getStartTime(), session.getEndTime())
            );
        }
    }
    
    private double calculateAccuracy(int hits, int shots) {
        return shots > 0 ? (double) hits / shots : 0.0;
    }
}