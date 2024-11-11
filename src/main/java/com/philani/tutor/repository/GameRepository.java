package com.philani.tutor.repository;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;

import java.util.List;
import java.util.Map;
import com.philani.tutor.model.GameSession;

@Repository
public class GameRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public GameRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public GameSession createSession(int level) {
        String sql = """
            INSERT INTO game_sessions (level, start_time, weapons, problematic_sequences)
            VALUES (?, ?, '{}', '{}')
        """;
        
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, level);
            ps.setString(2, LocalDateTime.now().toString());
            return ps;
        }, keyHolder);
        
        return getSession(keyHolder.getKey().longValue());
    }
    
    @SuppressWarnings("deprecation")
    public GameSession getSession(long id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM game_sessions WHERE id = ?",
            new Object[]{id},
            (rs, rowNum) -> mapResultSetToGameSession(rs)
        );
    }
    
    public void updateSession(GameSession session) {
        String sql = """
            UPDATE game_sessions 
            SET kill_count = ?, hit_count = ?, fire_count = ?, 
                accuracy = ?, passed_count = ?, weapons = ?,
                problematic_sequences = ?, end_time = ?, 
                elapsed_time_seconds = ?
            WHERE id = ?
        """;
        
        try {
            jdbcTemplate.update(sql,
                session.getKillCount(),
                session.getHitCount(),
                session.getFireCount(),
                session.getAccuracy(),
                session.getPassedCount(),
                objectMapper.writeValueAsString(session.getWeapons()),
                objectMapper.writeValueAsString(session.getProblematicSequences()),
                session.getEndTime() != null ? session.getEndTime().toString() : null,
                session.getElapsedTimeSeconds(),
                session.getId()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize session data", e);
        }
    }
    
    @SuppressWarnings("deprecation")
    public List<String> getWordLibrary(int complexity) {
        return jdbcTemplate.queryForObject(
            "SELECT words FROM word_libraries WHERE complexity = ?",
            new Object[]{complexity},
            (rs, rowNum) -> Arrays.asList(rs.getString("words").split(","))
        );
    }
    
    @SuppressWarnings("deprecation")
    public List<GameSession> getTopScores(Integer level, int limit) {
        String sql = level != null ?
            "SELECT * FROM game_sessions WHERE level = ? ORDER BY kill_count DESC, accuracy DESC LIMIT ?" :
            "SELECT * FROM game_sessions ORDER BY level DESC, kill_count DESC, accuracy DESC LIMIT ?";
            
        Object[] params = level != null ? 
            new Object[]{level, limit} : 
            new Object[]{limit};
            
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> mapResultSetToGameSession(rs));
    }
    
    private GameSession mapResultSetToGameSession(ResultSet rs) throws SQLException {
        GameSession session = new GameSession();
        session.setId(rs.getLong("id"));
        session.setLevel(rs.getInt("level"));
        session.setKillCount(rs.getInt("kill_count"));
        session.setHitCount(rs.getInt("hit_count"));
        session.setFireCount(rs.getInt("fire_count"));
        session.setAccuracy(rs.getDouble("accuracy"));
        session.setPassedCount(rs.getInt("passed_count"));
        
        String startTimeStr = rs.getString("start_time");
        if (startTimeStr != null) {
            session.setStartTime(LocalDateTime.parse(startTimeStr));
        }
        
        String endTimeStr = rs.getString("end_time");
        if (endTimeStr != null) {
            session.setEndTime(LocalDateTime.parse(endTimeStr));
        }
        
        session.setElapsedTimeSeconds(rs.getLong("elapsed_time_seconds"));
        
        try {
            session.setWeapons(objectMapper.readValue(
                rs.getString("weapons"), 
                new TypeReference<Map<String, Integer>>() {}
            ));
            session.setProblematicSequences(objectMapper.readValue(
                rs.getString("problematic_sequences"), 
                new TypeReference<Map<String, Integer>>() {}
            ));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize session data", e);
        }
        
        return session;
    }
}