package com.group.contestback.repositories;

import com.group.contestback.models.TaskTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTypesRepo extends JpaRepository<TaskTypes, Integer>{
    TaskTypes findAllByName(String name);
}