package com.group.contestback.repositories;

import com.group.contestback.models.SolutionVariants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionVariantsRepo extends JpaRepository<SolutionVariants, Integer> {
    List<SolutionVariants> findAllByTaskId(Integer taskId);
}
