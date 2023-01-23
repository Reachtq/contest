package com.group.contestback.services;

import com.group.contestback.models.Comments;

import java.util.List;

public interface CommentsService {
    void addComment(Integer toTaskId, String comment, Integer courseId);
    List<Comments> getAllComments();
    List<Comments> getCommentsToTask(Integer toTaskId);
    void removeComment(Integer commentId);
}
