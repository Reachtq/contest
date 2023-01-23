package com.group.contestback.repositories;

import com.group.contestback.models.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepo extends JpaRepository<Groups, Integer> {
}