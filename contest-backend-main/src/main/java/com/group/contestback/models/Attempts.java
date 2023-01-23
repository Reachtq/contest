package com.group.contestback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attempts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="userid")
    private Integer userId;
    @Column(name="taskid")
    private Integer taskId;
    private Date time;
    private Boolean succeeded;
    private String solution;
    @Column(name="courseid")
    private Integer courseId;

    public Attempts(Integer userId, Integer taskId, Boolean succeeded, String solution, Integer courseId) {
        this.userId = userId;
        this.taskId = taskId;
        this.time = new Date();
        this.succeeded = succeeded;
        this.solution = solution;
        this.courseId = courseId;
    }
}
