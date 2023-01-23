package com.group.contestback.repositories;

import com.group.contestback.models.GroupCourses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupCoursesRepo extends JpaRepository<GroupCourses, Integer> {
    List<GroupCourses> findAllByGroupId(Integer groupId);
}
