package com.group.contestback.services;

import com.group.contestback.models.Attempts;
import com.group.contestback.models.Scores;
import com.group.contestback.responseTypes.*;

import java.text.ParseException;
import java.util.List;

public interface ScoresService {
    void addScore(Integer userId, Integer taskId, Integer courseId, Integer score, String review);
    List<Scores> getAllScores();
    void addAttempt(Attempts attempt);
    List<Attempts> getAllAttempts();
    ResultsResponse checkSQLSolution(Integer taskId, Integer courseId, String solution) throws ParseException;
    ResultScoreResponse checkSQLSolutionScore(Integer taskId, Integer courseId, String solution) throws ParseException;
    Integer checkSimpleSolution(Integer taskId, Integer courseId, List<Integer> solutionsId);
    List<ScoresResponse> getStudentScores();
    List<ScoresUser> getGroupScoresForTask(Integer groupId, Integer taskId);
    List<Attempts> getStudentAttemptsOnTask(Integer taskId);
    GroupCoursesScoresResponse getGroupScoresForCourse(Integer groupId, Integer taskId);
    List<GroupStudents> getAllManualAttempts(String courseId);
}
