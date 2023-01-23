package com.group.contestback.repositories;

import com.group.contestback.models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoursesRepo extends JpaRepository<Courses, Integer> {
    Optional<Courses> findById(Integer id);
}
