package com.group.contestback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="middlename")
    private String middleName;
    private String login;
    @Column(name="passhash")
    private String passHash;
    @Column(name="passsalt")
    private String passSalt;
    private String email;
    @Column(name="roleid")
    private Integer roleId;
    @Column(name="groupid")
    private Integer groupId;

    public AppUser(String firstName, String lastName, String middleName, String login, String passHash, String passSalt, String email, Integer roleId, Integer groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.login = login;
        this.passHash = passHash;
        this.passSalt = passSalt;
        this.email = email;
        this.roleId = roleId;
        this.groupId = groupId;
    }
}
