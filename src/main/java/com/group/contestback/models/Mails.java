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
public class Mails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="touseremail")
    private String toUserEmail;
    private String text;
    private Date date;

    public Mails(String toUserEmail, String text, Date date) {
        this.toUserEmail = toUserEmail;
        this.text = text;
        this.date = date;
    }
}
