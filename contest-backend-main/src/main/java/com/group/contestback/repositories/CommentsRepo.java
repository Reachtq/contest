package com.group.contestback.repositories;

import com.group.contestback.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepo extends JpaRepository<Comments, Integer> {
    List<Comments> getCommentsByToTaskIdAndDeletedIsFalse(Integer toTaskId);
}
