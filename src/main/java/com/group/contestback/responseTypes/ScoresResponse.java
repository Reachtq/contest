package com.group.contestback.responseTypes;

import com.group.contestback.models.Courses;
import com.group.contestback.models.Scores;
import com.group.contestback.models.Tasks;
import lombok.Data;

@Data
public class ScoresResponse {
    Scores score;
    Courses course;
    Tasks task;
    String teacherName;
    String teacherLastName;
    String teacherMiddleName;
}
