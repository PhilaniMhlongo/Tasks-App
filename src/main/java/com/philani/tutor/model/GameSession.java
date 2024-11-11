package com.philani.tutor.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;


@Data
public class GameSession {
    private Long id;
    private Integer level;
    private Integer killCount;
    private Integer hitCount;
    private Integer fireCount;
    private Double accuracy;
    private Integer passedCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long elapsedTimeSeconds;
    private Map<String, Integer> weapons = new HashMap<>();
    private Map<String, Integer> problematicSequences = new HashMap<>();
}
