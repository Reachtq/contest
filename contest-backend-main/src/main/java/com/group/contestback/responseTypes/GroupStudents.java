package com.group.contestback.responseTypes;

import com.group.contestback.models.Groups;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupStudents{
    Groups groups;
    List<UserTasks> userTasks = new ArrayList<>();
    public void addUser(UserPageResponse user, List<AttemptsTask> manualAttempts) {
        this.userTasks.add(new UserTasks(user, manualAttempts));
    }
}
@Data
class UserTasks{
    UserPageResponse user;
    List<AttemptsTask> manualAttempts;

    public UserTasks(UserPageResponse user, List<AttemptsTask> manualAttempts) {
        this.user = user;
        this.manualAttempts = manualAttempts;
    }
}