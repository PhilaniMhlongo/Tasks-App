package com.philani.tasks.mappers;

import com.philani.tasks.domain.dto.TaskListDto;

import com.philani.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}

