package com.philani.tutor.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class DatabaseInitializationService {
    private static final Logger logger = Logger.getLogger(DatabaseInitializationService.class.getName());
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initializeDatabase() {
        try {
            createTables();
            initializeWordLibrariesIfEmpty();
        } catch (SQLException e) {
            logger.severe("Database initialization failed: " + e.getMessage());
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private void createTables() throws SQLException {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS game_sessions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                level INTEGER NOT NULL,
                kill_count INTEGER DEFAULT 0,
                hit_count INTEGER DEFAULT 0,
                fire_count INTEGER DEFAULT 0,
                accuracy REAL DEFAULT 0,
                passed_count INTEGER DEFAULT 0,
                start_time TEXT NOT NULL,
                end_time TEXT,
                elapsed_time_seconds INTEGER,
                weapons TEXT,
                problematic_sequences TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """);

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS word_libraries (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                complexity INTEGER NOT NULL,
                words TEXT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                UNIQUE(complexity)
            )
        """);
        
        jdbcTemplate.execute(
            "CREATE INDEX IF NOT EXISTS idx_word_libraries_complexity ON word_libraries(complexity)"
        );
    }

    private void initializeWordLibrariesIfEmpty() throws SQLException {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM word_libraries", 
            Integer.class
        );
        
        if (count != null && count == 0) {
            initializeWordLibraries();
        }
    }

    private void initializeWordLibraries() {
        Map<Integer, List<String>> libraries = getDefaultWordLibraries();
        
        for (Map.Entry<Integer, List<String>> entry : libraries.entrySet()) {
            try {
                jdbcTemplate.update(
                    "INSERT INTO word_libraries (complexity, words) VALUES (?, ?)",
                    entry.getKey(),
                    String.join(",", entry.getValue())
                );
                logger.info("Initialized word library for complexity level: " + entry.getKey());
            } catch (Exception e) {
                logger.warning("Failed to initialize word library for complexity " + 
                    entry.getKey() + ": " + e.getMessage());
            }
        }
    }

    private Map<Integer, List<String>> getDefaultWordLibraries() {
        Map<Integer, List<String>> libraries = new HashMap<>();
        
        // Basic programming keywords and short terms
        libraries.put(1, Arrays.asList(
            "nil", "lib", "go", "pen", "var", "if", "add", "int", "for", "len",
            "map", "set", "get", "put", "run", "new", "try"
        ));
        
        // Medium length programming terms
        libraries.put(2, Arrays.asList(
            "void", "null", "code", "scss", "bash", "else", "class", 
            "const", "break", "while", "final", "throw", "catch",
            "super", "print", "array"
        ));
        
        // More complex programming terms
        libraries.put(3, Arrays.asList(
            "interface", "abstract", "function", "boolean", "extends",
            "implement", "override", "private", "public", "static",
            "return", "import", "export", "default"
        ));
        
        return libraries;
    }
}