
package com.philani.tasks.mappers.impl;

import com.philani.tasks.domain.dto.TaskListDto;
import com.philani.tasks.domain.entities.Task;

import com.philani.tasks.domain.entities.TaskList;
import com.philani.tasks.domain.entities.TaskStatus;



import com.philani.tasks.mappers.TaskMapper;
import com.philani.tasks.mappers.TaskListMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper){

        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {

        return new TaskList(
            taskListDto.id()
            taskListDto.title(),
            taskListDto.description(),
            Optional.ofNullable(taskListDto.task())
                .map(tasks -> tasks.stream()
                    .map(taskMapper::fromDto)
                    .toList()).orElse(null),
                null,
                null);
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
        taskList.getId(),
        taskList.getTitle(),
        taskList.getDescription(),
        Optional.ofNullable(taskList.getTask())
            .map(List::size)
            .orElse(0),
        calculateTaskListProgress(taskList.getTask()),
        Optional.ofNullable(taskList.getTask())
            .map(task -> tasks.stream().map(taskMapper::toDto).toList()).orElse(null)
        );
    }


    private Double calculateTaskListProgress(List<Task> tasks){

        if(null==tasks) {

            return null;
        }

        long closedTaskCount = tasks.stream().filter(task -> 
            TaskStatus.CLOSED == task.getStatus()).count();

        return (double) closedTaskCount / tasks.size();


    }



}

