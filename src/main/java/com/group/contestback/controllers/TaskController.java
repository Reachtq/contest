package com.group.contestback.controllers;

import com.group.contestback.models.Tasks;
import com.group.contestback.services.AppUserService;
import com.group.contestback.services.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Task controller"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequestMapping("/task")
public class TaskController {
    private final AppUserService userService;
    private final TaskService taskService;

    @ApiOperation(value = "Добавляет новый тип заданий")
    @PostMapping("/addtasktype/{name}")
    public ResponseEntity<?> addTaskType(@PathVariable String name) {
        taskService.addTaskType(name);
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Добавляет вариант решения к задания")
    @PostMapping("/addsolutionVariant")
    public ResponseEntity<?> addTaskType(@RequestBody addSolutionVariant form) {
        taskService.addSolutionVariant(form.getSolution(),form.getIsAnswer(), form.getTaskId());
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Возращает все типы заданий")
    @GetMapping("/taskTypes")
    public ResponseEntity<?> getTaskTypes() {
        return ResponseEntity.ok().body(taskService.getTaskTypes());
    }
    @ApiOperation(value = "Добавляет новое задание")
    @PostMapping("/addtask")
    public ResponseEntity<?> addTask(@RequestBody addTaskForm form) {
        taskService.addTask(form.getName(), form.getSolution(), form.getDescription(), form.getTaskTypeId());
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Редактирование задания")
    @PostMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody Tasks task) {
        taskService.updateTask(task);
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Возращает все задания")
    @GetMapping("/allTasks")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok().body(taskService.getTasks());
    }

    @ApiOperation(value = "Возращает задание по курсу")
    @GetMapping("/get/{taskId}/{courseId}")
    public ResponseEntity<?> getTaskCourse(@PathVariable String taskId, @PathVariable String courseId)  {
        return ResponseEntity.ok().body(taskService.getTask(Integer.parseInt(taskId), Integer.parseInt(courseId)));
    }
    @ApiOperation(value = "Возращает задание по id")
    @GetMapping("/get/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable String taskId)  {
        return ResponseEntity.ok().body(taskService.getTask(Integer.parseInt(taskId)));
    }
    @ApiOperation(value = "Возращает все задания курса")
    @GetMapping("/allTasksByCourse/{courseId}")
    public ResponseEntity<?> getAllTasksByCourse(@PathVariable String courseId) {
        return ResponseEntity.ok().body(taskService.getTasksByCourse(Integer.parseInt(courseId)));
    }

    @ApiOperation(value = "Возращает все курсы")
    @GetMapping("/allCourses")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok().body(taskService.getAllCourses());
    }

    @ApiOperation(value = "Возращает все курсы студента")
    @GetMapping("/studentCourses")
    public ResponseEntity<?> getAllStudentCourses() {
        return ResponseEntity.ok().body(taskService.getStudentCourses());
    }

    @ApiOperation(value = "Добавляет новый курс")
    @PostMapping("/addCourse")
    public ResponseEntity<?> addCourse(@RequestBody addCourseForm form) {
        taskService.addCourse(form.getName(), form.getYear());
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Добавляет задание на курс")
    @PostMapping("/addTaskToCourse")
    public ResponseEntity<?> addTaskToCourse(@RequestBody addTaskToCourseForm form) {
        taskService.addTaskToCourse(form.getTaskId(), form.getCourseId());
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Возращает все группы")
    @GetMapping("/allGroups")
    public ResponseEntity<?> getAllGroups() {
        return ResponseEntity.ok().body(taskService.getAllGroups());
    }
    @ApiOperation(value = "Добавляет новую группу")
    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@RequestBody addGroupForm form) {
        taskService.addGroup(form.getNumber(), form.getYear());
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Возращает таблицу со всеми группами и их курсами")
    @GetMapping("/allGroupsCourses")
    public ResponseEntity<?> getAllGroupsCourses() {
        return ResponseEntity.ok().body(taskService.getAllGroupCourses());
    }
    @ApiOperation(value = "Добавляет группу на курс")
    @PostMapping("/addGroupOnCourse")
    public ResponseEntity<?> addGroupOnCourse(@RequestBody addGroupOnCourse form) {
        taskService.addGroupOnCourse(form.getCourseId(), form.getGroupId());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Добавляет дедлайн определенному заданию курса")
    @PostMapping("/addTaskDeadline")
    public ResponseEntity<?> addTaskDeadline(@RequestBody addTaskDeadline form) {
        taskService.addTaskDeadline(form.getTaskId(), form.getCourseId(), form.getDeadline());
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Возращает дедлайн к заданию")
    @GetMapping("/taskDeadline/{taskId}/{courseId}")
    public ResponseEntity<?> getTaskDeadline(@PathVariable String taskId, @PathVariable String courseId) {
        return ResponseEntity.ok().body(taskService.getTaskDeadline(Integer.parseInt(taskId), (Integer.parseInt(courseId))));
    }
}
@Data
class addTaskForm {
    private String name;
    private String solution;
    private String description;
    private Integer taskTypeId;
}
@Data
class addSolutionVariant {
    private String solution;
    private Boolean isAnswer;
    private Integer taskId;
}
@Data
class addCourseForm {
    private String name;
    private Integer year;
}
@Data
class addTaskToCourseForm {
    private Integer taskId;
    private Integer courseId;
}
@Data
class addGroupForm {
    private String number;
    private Integer year;
}
@Data
class addGroupOnCourse {
    private Integer courseId;
    private Integer groupId;
}
@Data
class addTaskDeadline {
    private Integer taskId;
    private Integer courseId;
    private String deadline;
}
@Data
class getTaskDeadLine {
    private Integer taskId;
    private Integer courseId;
}