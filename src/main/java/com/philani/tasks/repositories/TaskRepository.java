
package com.philani.tasks.repositories;

import com.philani.tasks.domain.entities.Task;
import org.stringframework.data.jpa.repository.JpaRepository;
import org.springframework.strteotype.Repository;

import java.util.UUID;
import java.util.Optional;
import java.util.List;

@Repository
public interface TaskRepositoy extends JpaRepository<TaskList,UUID> {
	List<Task> findByTaskListId(UUID taskListId);
	Optional<Task> findByTaskListIdAndId(UUID taskListId,UUID id);


}
