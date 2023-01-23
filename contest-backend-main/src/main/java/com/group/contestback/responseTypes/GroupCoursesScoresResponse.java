package com.group.contestback.responseTypes;

import com.group.contestback.models.Scores;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupCoursesScoresResponse {
    List<User> users = new ArrayList<>();
    public void addUser(List<Scores> scores, Integer id, String login, String firstName, String lastName, String middleName, String email, Integer roleId, Integer groupId) {
        User user = new User();
        user.setScores(scores);
        user.setId(id);
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setEmail(email);
        user.setRoleId(roleId);
        user.setGroupId(groupId);
        users.add(user);
    }
}

@Data
class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
    private String email;
    private Integer roleId;
    private Integer groupId;
    private List<Scores> scores;
}

