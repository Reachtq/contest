package com.group.contestback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scores {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="userid")
    private Integer userId;
    @Column(name="taskid")
    private Integer taskId;
    private Integer score;
    private Date date;
    @Column(name="teacherid")
    private Integer teacherId;
    private String solution;
    @Column(name="courseid")
    private Integer courseId;
    private String review;

    public Scores(Integer userId, Integer taskId, Integer score, Integer teacherId, String review, Integer courseId) {
        this.userId = userId;
        this.taskId = taskId;
        this.score = score;
        this.date = new Date();
        this.teacherId = teacherId;
        this.review = review;
        this.courseId = courseId;
    }
    public Scores(Integer userId, Integer taskId, Integer score, Integer teacherId, Integer courseId, String solution) {
        this.userId = userId;
        this.taskId = taskId;
        this.score = score;
        if(solution != null){
            this.date = new Date();
        }
        this.teacherId = teacherId;
        this.solution = solution;
        this.courseId = courseId;
    }
}
