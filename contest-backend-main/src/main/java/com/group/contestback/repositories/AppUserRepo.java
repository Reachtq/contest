package com.group.contestback.repositories;

import com.group.contestback.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findById(Integer id);
    AppUser findByLogin(String login);
    List<AppUser> findAllByGroupId(Integer groupId);


    @Query(
            value = "select * from users where users.firstname like ?1 or users.lastname like ?1",
            nativeQuery = true)
    Page<AppUser> nameSearch(String text, Pageable pageable);
}
