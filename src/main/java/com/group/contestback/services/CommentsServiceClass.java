package com.group.contestback.services;

import com.group.contestback.models.AppUser;
import com.group.contestback.models.Comments;
import com.group.contestback.repositories.AppUserRepo;
import com.group.contestback.repositories.CommentsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentsServiceClass implements CommentsService {
    private final CommentsRepo commentsRepo;
    private final AppUserRepo appUserRepo;
    @Override
    public void addComment(Integer toTaskId, String comment, Integer courseId) {
        AppUser appUser = appUserRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        commentsRepo.save(new Comments(toTaskId, appUser.getId(), comment, courseId));
    }

    @Override
    public List<Comments> getAllComments() {
        return commentsRepo.findAll();
    }

    @Override
    public List<Comments> getCommentsToTask(Integer toTaskId) {
        return commentsRepo.getCommentsByToTaskIdAndDeletedIsFalse(toTaskId);
    }

    @Override
    public void removeComment(Integer commentId) {
        Comments comments = commentsRepo.getById(commentId);
        comments.setDeleted(true);
        commentsRepo.save(comments);
    }
}
