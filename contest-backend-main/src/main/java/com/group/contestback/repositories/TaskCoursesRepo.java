package com.group.contestback.repositories;

import com.group.contestback.models.TaskCourses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCoursesRepo  extends JpaRepository<TaskCourses, Integer> {
    List<TaskCourses> findAllByCourseId(Integer courseId);
}
