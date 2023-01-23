package com.group.contestback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolutionVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String solution;
    private Boolean isAnswer;
    @Column(name="taskid")
    private Integer taskId;

    public SolutionVariants(String solution, Boolean isAnswer, Integer taskId) {
        this.solution = solution;
        this.isAnswer = isAnswer;
        this.taskId = taskId;
    }
}
