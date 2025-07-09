package com.philani.tasks.repositories;

import com.philani.tasks.domain.entities.TaskList;
import org.stringframework.data.jpa.repository.JpaRepository;
import org.springframework.strteotype.Repository;

import java.util.UUID;

@Repository
public interface TaskListRepositoy extends JpaRepository<TaskList,UUID> {}
