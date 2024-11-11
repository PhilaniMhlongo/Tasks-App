package com.philani.tutor.model;
import java.util.Map;

import lombok.Data;
@Data
public class GameProgressUpdate {
    private Integer killCount;
    private Integer hitCount;
    private Integer fireCount;
    private Integer passedCount;
    private Map<String, Integer> weapons;
    private Map<String, Integer> problematicSequences;
    private boolean gameOver;
}