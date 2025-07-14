package com.philani.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import com.philani.tasks.domain.entities.TaskList;

public class TestDataUtil {

    public static TaskList createTestTaskListA() {
        return new TaskList(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Groceries",
                "Buy vegetables, fruits, and milk",
                null,
                LocalDateTime.of(2023, 1, 1, 10, 0),
                LocalDateTime.of(2023, 1, 1, 10, 0)
        );
    }

    public static TaskList createTestTaskListB() {
        return new TaskList(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Work",
                "Prepare presentation for Monday",
                null,
                LocalDateTime.of(2023, 1, 2, 12, 0),
                LocalDateTime.of(2023, 1, 2, 12, 0)
        );
    }

    public static TaskList createTestTaskListWithoutId() {
        return new TaskList(
                null,
                "Home Repairs",
                "Fix the leaking sink",
                null,
                null,
                null
        );
    }
}
