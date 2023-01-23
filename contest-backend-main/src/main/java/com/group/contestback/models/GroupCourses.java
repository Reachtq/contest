package com.group.contestback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groupcourses")
public class GroupCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="courseid")
    private Integer courseId;
    @Column(name="groupid")
    private Integer groupId;

    public GroupCourses(int courseId, int groupId) {
        this.courseId = courseId;
        this.groupId = groupId;
    }
}
