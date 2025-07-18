
package com.philani.tasks.service.impl;

import com.philani.tasks.domain.entities.Task;
import com.philani.tasks.domain.entities.TaskList;
import com.philani.tasks.domain.entities.TaskPriority;
import com.philani.tasks.domain.entities.TaskStatus;
import com.philani.tasks.repositories.TaskListRepository;
import com.philani.tasks.repositories.TaskRepository;
import com.philani.tasks.TestDataUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskServiceImpl underTest;

    @Test
    public void testThatListTasksReturnsTasksFromRepository() {
        UUID taskListId = UUID.randomUUID();
        List<Task> tasks = List.of(TestDataUtil.createTestTaskA(), TestDataUtil.createTestTaskB());

        when(taskRepository.findByTaskListId(taskListId)).thenReturn(tasks);

        List<Task> result = underTest.listTasks(taskListId);

        assertThat(result).isEqualTo(tasks);
        verify(taskRepository).findByTaskListId(taskListId);
    }

    @Test
    public void testThatCreateTaskSavesNewTask() {
        UUID taskListId = UUID.randomUUID();
        Task inputTask = TestDataUtil.createTestTaskWithoutId();
        TaskList taskList = TestDataUtil.createTestTaskList();
        Task savedTask = TestDataUtil.createTestTaskA();

        when(taskListRepository.findById(taskListId)).thenReturn(Optional.of(taskList));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = underTest.createTask(taskListId, inputTask);

        assertThat(result).isEqualTo(savedTask);
        verify(taskListRepository).findById(taskListId);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    public void testThatCreateTaskThrowsWhenTitleIsBlank() {
        UUID taskListId = UUID.randomUUID();
        Task task = new Task();
        task.setTitle(" ");

        assertThatThrownBy(() -> underTest.createTask(taskListId, task))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task must have a title!");
    }

    @Test
    public void testThatCreateTaskThrowsWhenIdAlreadySet() {
        UUID taskListId = UUID.randomUUID();
        Task task = TestDataUtil.createTestTaskA();

        assertThatThrownBy(() -> underTest.createTask(taskListId, task))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task already has an ID!");
    }

    @Test
    public void testThatGetTaskReturnsTask() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Task task = TestDataUtil.createTestTaskA();

        when(taskRepository.findByTaskListIdAndId(taskListId, taskId)).thenReturn(Optional.of(task));

        Optional<Task> result = underTest.getTask(taskListId, taskId);

        assertThat(result).isPresent().contains(task);
    }

    @Test
    public void testThatUpdateTaskUpdatesAndSavesTask() {
        UUID taskListId = UUID.randomUUID();
        Task existing = TestDataUtil.createTestTaskA();
        Task update = TestDataUtil.createTestTaskA();
        update.setTitle("Updated Title");
        update.setPriority(TaskPriority.HIGH);
        update.setStatus(TaskStatus.CLOSED);

        when(taskRepository.findByTaskListIdAndId(taskListId, update.getId())).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(update);

        Task result = underTest.updateTask(taskListId, update.getId(), update);

        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getPriority()).isEqualTo(TaskPriority.HIGH);
        assertThat(result.getStatus()).isEqualTo(TaskStatus.CLOSED);
    }

    @Test
    public void testThatUpdateTaskThrowsIfIdIsNull() {
        UUID taskListId = UUID.randomUUID();
        Task update = new Task();

        assertThatThrownBy(() -> underTest.updateTask(taskListId, UUID.randomUUID(), update))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task must have ID!");
    }

    @Test
    public void testThatDeleteTaskCallsRepository() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();

        underTest.deleteTask(taskListId, taskId);

        verify(taskRepository).deleteByTaskListIdAndId(taskListId, taskId);
    }
}
