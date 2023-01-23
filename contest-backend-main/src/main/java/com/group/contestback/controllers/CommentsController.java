package com.group.contestback.controllers;

import com.group.contestback.models.Comments;
import com.group.contestback.services.CommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Comments controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CommentsController {
    private final CommentsService commentsService;
    @ApiOperation(value = "Добавляет новый комментарий")
    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody CommentsForm commentsForm) {
        commentsService.addComment(commentsForm.getToTaskId(), commentsForm.getComment(), commentsForm.getCourseId());
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Возращает все коментарии")
    @GetMapping("/allComments")
    public ResponseEntity<?> getAllComments() {
        return ResponseEntity.ok().body(commentsService.getAllComments());
    }
    @ApiOperation(value = "Возращает все коментарии к заданию")
    @GetMapping("/getCommentsToTask/{toTaskId}")
    public ResponseEntity<?> getAllCommentsToTask(@PathVariable String toTaskId) {
        return ResponseEntity.ok().body(commentsService.getCommentsToTask(Integer.parseInt(toTaskId)));
    }
    @ApiOperation(value = "Удаление комментария")
    @PostMapping("/removeComment/{commentId}")
    public ResponseEntity<?> removeComment(@PathVariable String commentId) {
        commentsService.removeComment(Integer.parseInt(commentId));
        return ResponseEntity.ok().build();
    }
}
@Data
class CommentsForm {
    private Integer toTaskId;
    private Integer courseId;
    private String comment;
}