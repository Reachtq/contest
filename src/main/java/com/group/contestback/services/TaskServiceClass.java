package com.group.contestback.services;

import com.group.contestback.models.*;
import com.group.contestback.repositories.*;
import com.group.contestback.responseTypes.GroupCoursesWithNames;
import com.group.contestback.responseTypes.StudentTaskResponse;
import com.group.contestback.responseTypes.TaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceClass implements TaskService {
    private final TasksRepo tasksRepo;
    private final TaskTypesRepo taskTypesRepo;
    private final AppUserService appUserService;
    private final CoursesRepo coursesRepo;
    private final TaskCoursesRepo taskCoursesRepo;
    private final GroupsRepo groupsRepo;
    private final GroupCoursesRepo groupCoursesRepo;
    private final AppUserRepo appUserRepo;
    private final SolutionVariantsRepo solutionVariantsRepo;
    private final TaskDeadlinesRepo taskDeadlinesRepo;
    private final AttemptsRepo attemptsRepo;

    @Override
    public void addTaskType(String name) {
        TaskTypes taskTypes = new TaskTypes(name);
        taskTypesRepo.save(taskTypes);
    }

    @Override
    public void addSolutionVariant(String solution, Boolean isAnswer, Integer taskId) {
        solutionVariantsRepo.save(new SolutionVariants(solution, isAnswer, taskId));
    }

    @Override
    public List<TaskTypes> getTaskTypes() {
        return taskTypesRepo.findAll();
    }

    @Override
    public void addTask(String name, String solution, String description, Integer taskTypeId) {
        try {
            Tasks tasks = new Tasks(name, solution, description, taskTypeId);
            tasksRepo.save(tasks);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void fillTasks(List<TaskResponse> taskResponses, Tasks task, TaskResponse taskResponse, Integer courseId) {
        try {
            taskResponse.setTask(new Tasks(task.getId(), task.getName(), task.getDescription(), "", task.getTaskTypeId()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        List<TaskDeadlines> tdl = taskDeadlinesRepo.findAllByTaskIdAndCourseId(task.getId(), courseId);
        if(tdl.size() > 0) {
            taskResponse.setDeadline(tdl.get(0).getDeadline().toString());
        }
        List<SolutionVariants> solutionVariants = solutionVariantsRepo.findAllByTaskId(task.getId());
        for (int k = 0; k < solutionVariants.size(); ++k) {
            taskResponse.addSolutionVariant(solutionVariants.get(k).getId(), solutionVariants.get(k).getSolution(), solutionVariants.get(k).getTaskId());
        }
        taskResponses.add(taskResponse);
    }

    @Override
    public List<Tasks> getTasks() {
        return tasksRepo.findAll();
    }

    @Override
    public List<TaskResponse> getTasksByCourse(Integer courseId) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        List<TaskCourses> taskCourses = taskCoursesRepo.findAllByCourseId(courseId);
        for (int i = 0; i < taskCourses.size(); ++i) {
            TaskResponse taskResponse = new TaskResponse();
            Tasks task = tasksRepo.findById(taskCourses.get(i).getTaskId()).get();
            fillTasks(taskResponses, task, taskResponse, courseId);
        }
        return taskResponses;
    }


    @Override
    public List<Courses> getAllCourses() {
        return coursesRepo.findAll();
    }

    @Override
    public void addCourse(String name, Integer year) {
        Courses courses = new Courses(name, year);
        coursesRepo.save(courses);
    }

    @Override
    public void addTaskToCourse(Integer taskId, Integer courseId) {
        TaskCourses taskCourses = new TaskCourses(taskId, courseId);
        taskCoursesRepo.save(taskCourses);
    }

    @Override
    public List<Groups> getAllGroups() {
        return groupsRepo.findAll();
    }

    @Override
    public void addGroup(String number, Integer year) {
        Groups groups = new Groups(number, year);
        groupsRepo.save(groups);
    }

    @Override
    public List<GroupCoursesWithNames> getAllGroupCourses() {
        List<GroupCoursesWithNames> groupCoursesWithNames = new ArrayList<>();
        List<Groups> allGroups = groupsRepo.findAll();
//        groupCoursesRepo.findAll().forEach(groupCourses -> groupCoursesWithNames.add(new GroupCoursesWithNames(
//                        groupCourses.getId()
//                        , coursesRepo.getById(groupCourses.getCourseId()).getName(),
//                        groupCourses.getCourseId(),
//                        groupsRepo.getById(groupCourses.getGroupId()).getNumber(),
//                        groupCourses.getGroupId()
//                ))
//        );
        allGroups.forEach(group -> {
            GroupCoursesWithNames gwn = new GroupCoursesWithNames();
            gwn.setGroupId(group.getId());
            Groups group1 = groupsRepo.getById(group.getId());
            gwn.setGroupName(group1.getNumber());
            gwn.setGroupYear(group1.getYear());
            List<Courses> courses = new ArrayList<>();
            groupCoursesRepo.findAllByGroupId(group.getId()).forEach(groupCourses -> {
                Courses course = coursesRepo.findById(groupCourses.getCourseId()).get();
                courses.add(new Courses(course.getId(), course.getName(), course.getYear()));
            });
            gwn.setCourses(courses);
            groupCoursesWithNames.add(gwn);
        });
        return groupCoursesWithNames;
    }

    @Override
    public void addGroupOnCourse(Integer courseId, Integer groupId) {
        GroupCourses groupCourses = new GroupCourses(courseId, groupId);
        groupCoursesRepo.save(groupCourses);
    }

    @Override
    public String getTaskDeadline(Integer taskId, Integer courseId) {
        return taskDeadlinesRepo.findAllByTaskIdAndCourseId(taskId, courseId).get(0).getDeadline().toString();
    }

    @Override
    public void addTaskDeadline(Integer taskId, Integer courseId, String deadline) {
        try {
            taskDeadlinesRepo.save(new TaskDeadlines(deadline, taskId, courseId));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateTask(Tasks task) {
        Tasks taskOld = tasksRepo.getById(task.getId());
        taskOld.setTaskTypeId(task.getTaskTypeId());
        taskOld.setDescription(task.getDescription());
        taskOld.setName(task.getName());
        taskOld.setSolution(task.getSolution());
        tasksRepo.save(taskOld);
    }

    @Override
    public TaskResponse getTask(Integer taskId, Integer courseId) {
        Tasks task = tasksRepo.getById(taskId);
        TaskResponse taskResponse = new TaskResponse();
        try {
            taskResponse.setTask(new Tasks(task.getId(), task.getName(), task.getDescription(), "", task.getTaskTypeId()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        List<TaskDeadlines> tdl = taskDeadlinesRepo.findAllByTaskIdAndCourseId(task.getId(), courseId);
        if(tdl.size() > 0) {
            taskResponse.setDeadline(tdl.get(0).getDeadline().toString());
        }
        List<SolutionVariants> solutionVariants = solutionVariantsRepo.findAllByTaskId(task.getId());
        for (int k = 0; k < solutionVariants.size(); ++k) {
            taskResponse.addSolutionVariant(solutionVariants.get(k).getId(), solutionVariants.get(k).getSolution(), solutionVariants.get(k).getTaskId());
        }
        return taskResponse;
    }

    @Override
    public TaskResponse getTask(Integer taskId) {
        Tasks task = tasksRepo.getById(taskId);
        TaskResponse taskResponse = new TaskResponse();
        try {
            taskResponse.setTask(new Tasks(task.getId(), task.getName(), task.getDescription(), task.getSolution(), task.getTaskTypeId()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        List<SolutionVariants> solutionVariants = solutionVariantsRepo.findAllByTaskId(task.getId());
        for (int k = 0; k < solutionVariants.size(); ++k) {
            taskResponse.addSolutionVariant(solutionVariants.get(k).getId(), solutionVariants.get(k).getSolution(), solutionVariants.get(k).getTaskId(), solutionVariants.get(k).getIsAnswer());
        }
        return taskResponse;
    }

    @Override
    public List<StudentTaskResponse> getStudentCourses() {
        AppUser appUser = appUserRepo.findByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        List<GroupCourses> groupCourses
                = groupCoursesRepo.findAllByGroupId(appUser.getGroupId());
        List<Courses> courses = new ArrayList<>();

        List<StudentTaskResponse> resp = new ArrayList<>();

        for (int i = 0; i < groupCourses.size(); ++i) {
            StudentTaskResponse studentTaskResponse = new StudentTaskResponse();

            Courses courses1 = coursesRepo.findById(groupCourses.get(i).getCourseId()).get();
            studentTaskResponse.setUserId(appUser.getId());
            studentTaskResponse.setCourses(courses1);
            List<Attempts> userAttempts = attemptsRepo.findAllByCourseIdAndUserId(courses1.getId(), appUser.getId());
            List<TaskCourses> taskCourses = taskCoursesRepo.findAllByCourseId(courses1.getId());
            Set<Integer> set = new HashSet<Integer> ();



            for(int k = 0; k < userAttempts.size(); ++k) {
                set.add(userAttempts.get(k).getTaskId());
            }
            studentTaskResponse.setCompletion(set.size()/taskCourses.size());

            List<TaskDeadlines> taskDeadlines = taskDeadlinesRepo.findAllByCourseId(courses1.getId());
            Comparator<TaskDeadlines> comparator = (p1, p2) -> (int) (p2.getDeadline().getTime() - p1.getDeadline().getTime());
            taskDeadlines.sort(comparator);
            if(taskDeadlines.size() > 0) {
                studentTaskResponse.setNearestDeadline(taskDeadlines.get(0).getDeadline());
            }
            resp.add(studentTaskResponse);
        }

        return resp;
    }
}
