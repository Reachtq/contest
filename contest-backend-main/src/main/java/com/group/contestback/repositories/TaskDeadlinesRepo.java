package com.group.contestback.repositories;

import com.group.contestback.models.TaskDeadlines;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskDeadlinesRepo extends JpaRepository<TaskDeadlines, Integer> {
    List<TaskDeadlines> findAllByTaskIdAndCourseId(Integer taskId, Integer courseId);
    List<TaskDeadlines> findAllByCourseId(Integer courseId);
}
