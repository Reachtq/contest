package com.group.contestback.responseTypes;

import com.group.contestback.models.Courses;
import lombok.Data;

import java.util.Date;

@Data
public class StudentTaskResponse {
    private Integer userId;
    private Courses courses;
    private Integer completion;
    private Date nearestDeadline;
}
