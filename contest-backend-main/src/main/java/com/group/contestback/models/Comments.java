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
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="totaskid")
    private Integer toTaskId;
    @Column(name="fromuserid")
    private Integer fromUserId;
    private Date date;
    private Boolean deleted;
    private String comment;
    @Column(name="courseid")
    private Integer courseId;

    public Comments(Integer toTaskId, Integer fromUserId, String solution, Integer courseId) {
        this.toTaskId = toTaskId;
        this.fromUserId = fromUserId;
        this.comment = solution;
        this.deleted = false;
        this.date = new Date();
        this.courseId = courseId;
        //this.date = java.sql.Date.valueOf(String.valueOf(LocalDateTime.now()));
    }
}
