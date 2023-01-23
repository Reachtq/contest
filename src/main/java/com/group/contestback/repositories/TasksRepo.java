package com.group.contestback.repositories;

import com.group.contestback.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepo extends JpaRepository<Tasks, Integer> {
    List<Tasks> findAllByTaskTypeId(Integer taskTypeId);
}