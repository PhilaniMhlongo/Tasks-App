package com.philani.tasks.service;

import java.util.List;
import java.util.UUID;

import com.philani.tasks.domain.entities.Task;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
}