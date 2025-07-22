package com.philani.tasks.controllers;

import com.philani.tasks.domain.dto.TaskDto;
import com.philani.tasks.domain.dto.TaskListDto;
import com.philani.tasks.domain.entities.Task;
import com.philani.tasks.mappers.TaskMapper;
import com.philani.tasks.service.TaskService;
import com.philani.tasks.TestDataUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TasksControllerTests {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TasksController tasksController;

    @Test
    void listTasks_returnsListOfTaskDtos() {
        UUID taskListId = UUID.randomUUID();
        Task task = TestDataUtil.createTestTaskA();
        TaskDto taskDto = TestDataUtil.createTestTaskDtoA();

        when(taskService.listTasks(taskListId)).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        List<TaskDto> result = tasksController.listTasks(taskListId);

        ResponseEntity<List<TaskDto>> response = new ResponseEntity<>(result, HttpStatus.OK);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result).containsExactly(taskDto);
        verify(taskService).listTasks(taskListId);
    }

    @Test
    void createTask_returnsCreatedTaskDto() {
        UUID taskListId = UUID.randomUUID();
        TaskDto inputDto = TestDataUtil.createTestTaskDtoA();
        Task task = TestDataUtil.createTestTaskA();
        Task createdTask = TestDataUtil.createTestTaskB();
        TaskDto outputDto = TestDataUtil.createTestTaskDtoB();

        when(taskMapper.fromDto(inputDto)).thenReturn(task);
        when(taskService.createTask(taskListId, task)).thenReturn(createdTask);
        when(taskMapper.toDto(createdTask)).thenReturn(outputDto);

        TaskDto result = tasksController.createTask(taskListId, inputDto);

        assertThat(result).isEqualTo(outputDto);
        verify(taskService).createTask(taskListId, task);
    }

    @Test
    void getTask_returnsOptionalOfTaskDto() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Task task = TestDataUtil.createTestTaskA();
        TaskDto taskDto = TestDataUtil.createTestTaskDtoA();

        when(taskService.getTask(taskListId, taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        Optional<TaskDto> result = tasksController.getTask(taskListId, taskId);

        ResponseEntity<TaskDto> response = new ResponseEntity<>(result.orElse(null), HttpStatus.OK);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(taskDto);
    }

    @Test
    void updateTask_returnsUpdatedTaskDto() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        TaskDto inputDto = TestDataUtil.createTestTaskDtoA();
        Task task = TestDataUtil.createTestTaskA();
        Task updatedTask = TestDataUtil.createTestTaskB();
        TaskDto outputDto = TestDataUtil.createTestTaskDtoB();

        when(taskMapper.fromDto(inputDto)).thenReturn(task);
        when(taskService.updateTask(taskListId, taskId, task)).thenReturn(updatedTask);
        when(taskMapper.toDto(updatedTask)).thenReturn(outputDto);

        TaskDto result = tasksController.updateTask(taskListId, taskId, inputDto);
        ResponseEntity<TaskDto> response = new ResponseEntity<>(result, HttpStatus.OK);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(result).isEqualTo(outputDto);
        verify(taskService).updateTask(taskListId, taskId, task);
    }

    @Test
    void deleteTask_invokesServiceDelete() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();

        tasksController.deleteTask(taskListId, taskId);

        verify(taskService).deleteTask(taskListId, taskId);
    }
}

