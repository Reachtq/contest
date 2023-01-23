package com.group.contestback.responseTypes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPageResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
    private String email;
    private Integer roleId;
    private Integer groupId;
    private String roleName;
    private String groupName;
}
