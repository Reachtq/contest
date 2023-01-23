package com.group.contestback.repositories;

import com.group.contestback.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepo extends JpaRepository<Roles,Integer> {
    List<Roles> findAllById(Integer Id);
    Roles findByName(String name);
}
