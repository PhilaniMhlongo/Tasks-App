
package com.philani.tasks.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.philani.tasks.TestDataUtil;
import com.philani.tasks.domain.entities.TaskList;
import com.philani.tasks.repositories.TaskListRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TaskListServiceImplTest {

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskListServiceImpl underTest;

   @Test
    public void testThatListTaskListsReturnsFromRepository() {
        // Given
        final List<TaskList> expectedTaskLists = List.of(
            TestDataUtil.createTestTaskListA(),
            TestDataUtil.createTestTaskListB()
        );
        when(taskListRepository.findAll()).thenReturn(expectedTaskLists);
        
        // When
        final List<TaskList> result = underTest.listTaskLists();
        
        // Then
        assertThat(result).isEqualTo(expectedTaskLists);
        verify(taskListRepository, times(1)).findAll();
    }
    @Test
    public void testThatCreateTaskListReturnsSavedTaskList() {
        // given
        TaskList input = TestDataUtil.createTestTaskListWithoutId();
        TaskList saved = new TaskList(
                UUID.randomUUID(),
                input.getTitle(),
                input.getDescription(),
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        when(taskListRepository.save(any(TaskList.class))).thenReturn(saved);

        // when
        TaskList result = underTest.createTaskList(input);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo(input.getTitle());
    }

    @Test
    public void testThatCreateTaskListThrowsIfIdPresent() {
        
        TaskList input = TestDataUtil.createTestTaskListA();

       
        assertThatThrownBy(() -> underTest.createTaskList(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task list already has an ID!");
    }

    @Test
    public void testThatCreateTaskListThrowsIfTitleMissing() {
    
        TaskList input = new TaskList(null, null, "Description", null, null, null);

    
        assertThatThrownBy(() -> underTest.createTaskList(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task list title must be present!");
    }

    @Test
    public void testThatGetTaskListReturnsExpectedOptional() {
        // given
        TaskList taskList = TestDataUtil.createTestTaskListA();
        when(taskListRepository.findById(taskList.getId())).thenReturn(Optional.of(taskList));

        // when
        Optional<TaskList> result = underTest.getTaskList(taskList.getId());

        // then
        assertThat(result).isPresent().contains(taskList);
    }

    @Test
    public void testThatUpdateTaskListUpdatesAndReturnsTaskList() {
        // given
        TaskList existing = TestDataUtil.createTestTaskListA();
        TaskList update = new TaskList(
                existing.getId(),
                "Updated Title",
                "Updated Description",
                null,
                existing.getCreated(),
                null
        );
        when(taskListRepository.findById(existing.getId())).thenReturn(Optional.of(existing));
        when(taskListRepository.save(any(TaskList.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        TaskList result = underTest.updateTaskList(existing.getId(), update);

        // then
        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    public void testThatUpdateTaskListThrowsIfIdMissing() {
        // given
        TaskList update = new TaskList(null, "Title", "Desc", null, null, null);

        // when / then
        assertThatThrownBy(() -> underTest.updateTaskList(UUID.randomUUID(), update))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task list must have an ID");
    }

    @Test
    public void testThatUpdateTaskListThrowsIfIdChanged() {
        // given
        TaskList update = TestDataUtil.createTestTaskListA();
        UUID differentId = UUID.randomUUID();

        // when / then
        assertThatThrownBy(() -> underTest.updateTaskList(differentId, update))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Attempting to change task list ID, this is not permitted!");
    }

    @Test
    public void testThatUpdateTaskListThrowsIfNotFound() {
        // given
        TaskList update = TestDataUtil.createTestTaskListA();
        when(taskListRepository.findById(update.getId())).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> underTest.updateTaskList(update.getId(), update))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task list not found!");
    }

    @Test
    public void testThatDeleteTaskListCallsRepository() {
        // given
        UUID taskListId = UUID.randomUUID();

        // when
        underTest.deleteTaskList(taskListId);

        // then
        verify(taskListRepository).deleteById(taskListId);
    }
}
