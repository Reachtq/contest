package com.group.contestback.responseTypes;

import com.group.contestback.models.Tasks;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskResponse {
    private Tasks task;
    private String deadline;
    public void addSolutionVariant(Integer id, String solution, Integer taskId) {
        this.solutionVariants.add(new SolutionVariant(id, solution, taskId));
    }
    public void addSolutionVariant(Integer id, String solution, Integer taskId, Boolean isAnswer) {
        this.solutionVariants.add(new SolutionVariant(id, solution, taskId, isAnswer));
    }
    private List<SolutionVariant> solutionVariants = new ArrayList<>();
}
@Data
class SolutionVariant {
    private Integer id;
    private String solution;
    private Integer taskId;
    private Boolean isAnswer;

    public SolutionVariant(Integer id, String solution, Integer taskId) {
        this.id = id;
        this.solution = solution;
        this.taskId = taskId;
        this.isAnswer = null;
    }
    public SolutionVariant(Integer id, String solution, Integer taskId, Boolean isAnswer) {
        this.id = id;
        this.solution = solution;
        this.taskId = taskId;
        this.isAnswer = isAnswer;
    }
}
