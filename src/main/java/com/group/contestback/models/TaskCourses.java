package com.group.contestback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "taskcourses")
public class TaskCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name="taskid")
    private Integer taskId;
    @Column(name="courseid")
    private Integer courseId;

    public TaskCourses(Integer taskId, Integer courseId) {
        this.taskId = taskId;
        this.courseId = courseId;
    }
}
