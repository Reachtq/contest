package com.group.contestback.responseTypes;
import com.group.contestback.models.Courses;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class GroupCoursesWithNames {
    private String groupName;
    private Integer groupId;
    private Integer groupYear;
    private List<Courses> courses;

    public GroupCoursesWithNames(String groupName, Integer groupId, List<Courses> courses) {
        this.courses = courses;
        this.groupName = groupName;
        this.groupId = groupId;
    }
}
