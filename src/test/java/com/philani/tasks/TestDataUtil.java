package com.philani.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.philani.tasks.domain.dto.TaskDto;
import com.philani.tasks.domain.dto.TaskListDto;
import com.philani.tasks.domain.entities.Task;
import com.philani.tasks.domain.entities.TaskList;
import com.philani.tasks.domain.entities.TaskPriority;
import com.philani.tasks.domain.entities.TaskStatus;

public class TestDataUtil {

    
    public static TaskList createTestTaskListA() {
        return new TaskList(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Groceries",
                "Buy vegetables, fruits, and milk",
                null,
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 1, 10, 0)
        );
    }

    public static TaskList createTestTaskListB() {
        return new TaskList(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Work",
                "Prepare presentation for Monday",
                null,
                LocalDateTime.of(2025, 1, 2, 12, 0),
                LocalDateTime.of(2025, 1, 2, 12, 0)
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


       public static Task createTestTaskA() {
        return new Task(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Test Task A",
                "Description for task A",
                LocalDateTime.now().plusDays(5),
                TaskStatus.OPEN,
                TaskPriority.MEDIUM,
                createTestTaskList(),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        );
    }

    public static Task createTestTaskB() {
        return new Task(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Test Task B",
                "Description for task B",
                LocalDateTime.now().plusDays(10),
                TaskStatus.OPEN,
                TaskPriority.HIGH,
                createTestTaskList(),
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now()
        );
    }

    public static Task createTestTaskWithoutId() {
        return new Task(
                null,
                "New Task",
                "A new task to be created",
                LocalDateTime.now().plusDays(3),
                null, // let the service apply default
                null, // let the service apply default
                createTestTaskList(),
                null,
                null
        );
    }

    public static TaskList createTestTaskList() {
        List<TaskDto> list=new ArrayList<>();
        return new TaskList(
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                "Test Task List",
                "A list of tasks for testing",
                null,
                LocalDateTime.now().minusDays(7),
                LocalDateTime.now()
               

        );

    }

    
    public static TaskListDto createTestTaskListDtoA() {
        List<TaskDto> list=new ArrayList<>();
        return new TaskListDto(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Personal Tasks",
                "Things to do at home",
                3,
                0.6,
                list
        );
    }

    public static TaskListDto createTestTaskListDtoB() {
        List<TaskDto> list=new ArrayList<>();
        return new TaskListDto(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Work Tasks",
                "Project deliverables",
                5,
                0.8,
                list
        );
    }

  public static TaskDto createTestTaskDtoA() {
        return new TaskDto(
                UUID.randomUUID(),
                "Task A",
                "Description A",
                LocalDateTime.of(2025, 7, 25, 12, 0),
                TaskPriority.MEDIUM,
                TaskStatus.OPEN
        );
    }

    public static TaskDto createTestTaskDtoB() {
        return new TaskDto(
                UUID.randomUUID(),
                "Task B",
                "Description B",
                LocalDateTime.of(2025, 8, 1, 10, 30),
                TaskPriority.HIGH,
                TaskStatus.OPEN
        );
    }
}
