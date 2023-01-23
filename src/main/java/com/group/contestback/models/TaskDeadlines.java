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
@Table(name = "taskdeadlines")
public class TaskDeadlines {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date deadline;
    @Column(name="taskid")
    private Integer taskId;
    @Column(name="courseid")
    private Integer courseId;

    public TaskDeadlines(String deadline, Integer taskId, Integer courseId) throws ParseException {

        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        this.deadline = formatter.parse(deadline);
        this.taskId = taskId;
        this.courseId = courseId;
    }
}
