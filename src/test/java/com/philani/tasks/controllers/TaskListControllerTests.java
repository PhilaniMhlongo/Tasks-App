package com.philani.tasks.controllers;

import com.philani.tasks.domain.dto.TaskListDto;
import com.philani.tasks.domain.entities.TaskList;
import com.philani.tasks.mappers.TaskListMapper;
import com.philani.tasks.service.TaskListService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TaskListControllerTests {

    @Mock
    private TaskListService taskListService;

    @Mock
    private TaskListMapper taskListMapper;

    @InjectMocks
    private TaskListController taskListController;

    // List TaskLists Tests
    @Test
    void testListTaskLists_ReturnsCorrectDtoAndStatus200() {
        TaskList taskList = TestDataUtil.createTestTaskListA();
        TaskListDto dto = TestDataUtil.createTestTaskListDtoA();

        when(taskListService.listTaskLists()).thenReturn(List.of(taskList));
        when(taskListMapper.toDto(taskList)).thenReturn(dto);

        List<TaskListDto> result = taskListController.listTaskLists();

        ResponseEntity<List<TaskListDto>> response = new ResponseEntity<>(result, HttpStatus.OK);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result).containsExactly(dto);
        verify(taskListService).listTaskLists();
        verify(taskListMapper).toDto(taskList);
    }

    @Test
    void testCreateTaskList_ReturnsCorrectDtoAndStatus200() {
        TaskListDto inputDto = TestDataUtil.createTestTaskListDtoA();
        TaskList entity = TestDataUtil.createTestTaskListA();
        TaskList savedEntity = TestDataUtil.createTestTaskListB();
        TaskListDto outputDto = TestDataUtil.createTestTaskListDtoB();

        when(taskListMapper.fromDto(inputDto)).thenReturn(entity);
        when(taskListService.createTaskList(entity)).thenReturn(savedEntity);
        when(taskListMapper.toDto(savedEntity)).thenReturn(outputDto);

        TaskListDto result = taskListController.createTaskList(inputDto);
        ResponseEntity<TaskListDto> response = new ResponseEntity<>(result, HttpStatus.OK);

        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(result).isEqualTo(outputDto);
        verify(taskListMapper).fromDto(inputDto);
        verify(taskListService).createTaskList(entity);
        verify(taskListMapper).toDto(savedEntity);
    }


    // Get TaskList Tests
    @Test
    void testGetTaskList_ReturnsCorrectDtoAndStatus200() {
        UUID id = UUID.randomUUID();
        TaskList entity = TestDataUtil.createTestTaskListA();
        TaskListDto dto = TestDataUtil.createTestTaskListDtoA();

        when(taskListService.getTaskList(id)).thenReturn(Optional.of(entity));
        when(taskListMapper.toDto(entity)).thenReturn(dto);

        Optional<TaskListDto> result = taskListController.getTaskList(id);

        ResponseEntity<TaskListDto> response = new ResponseEntity<>(result.orElse(null), HttpStatus.OK);

        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(dto);
        verify(taskListService).getTaskList(id);
        verify(taskListMapper).toDto(entity);
    }

    @Test
    void testGetTaskList_ReturnsEmptyWhenNotFound() {
        UUID id = UUID.randomUUID();

        when(taskListService.getTaskList(id)).thenReturn(Optional.empty());

        Optional<TaskListDto> result = taskListController.getTaskList(id);

        assertThat(result).isEmpty();
        verify(taskListService).getTaskList(id);
        verify(taskListMapper, never()).toDto(any());
    }

    // Update TaskList Tests
    @Test
    void testUpdateTaskList_ReturnsCorrectDtoAndStatus200() {
        UUID id = UUID.randomUUID();
        TaskListDto inputDto = TestDataUtil.createTestTaskListDtoA();
        TaskList inputEntity = TestDataUtil.createTestTaskListA();
        TaskList updatedEntity = TestDataUtil.createTestTaskListB();
        TaskListDto outputDto = TestDataUtil.createTestTaskListDtoB();

        when(taskListMapper.fromDto(inputDto)).thenReturn(inputEntity);
        when(taskListService.updateTaskList(id, inputEntity)).thenReturn(updatedEntity);
        when(taskListMapper.toDto(updatedEntity)).thenReturn(outputDto);

        TaskListDto result = taskListController.updateTaskList(id, inputDto);

        ResponseEntity<TaskListDto> response = new ResponseEntity<>(result, HttpStatus.OK);

        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(result).isEqualTo(outputDto);
        verify(taskListMapper).fromDto(inputDto);
        verify(taskListService).updateTaskList(id, inputEntity);
        verify(taskListMapper).toDto(updatedEntity);
    }


   @Test
    void testDeleteTaskList_CallsServiceDeleteAndStatus200() {
        UUID id = UUID.randomUUID();

        taskListController.deleteTaskList(id);
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskListService).deleteTaskList(id);
    }
}
