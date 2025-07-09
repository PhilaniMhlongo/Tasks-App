package com.philani.tasks.mappers;

import com.philani.tasks.domain.dto.TaskDto;
import com.philani.tasks.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
