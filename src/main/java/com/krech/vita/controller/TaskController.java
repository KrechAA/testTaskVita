package com.krech.vita.controller;

import com.krech.vita.domain.rest.request.TaskCreateRequest;
import com.krech.vita.domain.rest.request.TaskUpdateRequest;
import com.krech.vita.domain.rest.response.PaginationResult;
import com.krech.vita.domain.rest.response.TaskResponse;
import com.krech.vita.domain.db.Task;
import com.krech.vita.domain.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.krech.vita.service.TaskService;


/**
 * Controller for tasks
 */

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    /**
     * This method returned all tasks of user. Method for user.
     *
     * @param pageNumber number of requested page
     * @param pageSize size of requested page
     * @param ordering ordering by date
     * @return pagination object with tasks inside
     */
    @GetMapping(value = "/user/tasks/{userId}")
    public ResponseEntity<PaginationResult<Task>> getAllTasksByUser(@RequestParam(required = false, defaultValue = "1") int pageNumber,
                                                                    @RequestParam(required = false, defaultValue = "5") int pageSize,
                                                                    @RequestParam(required = false, defaultValue = "desc") String ordering) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<>(taskService.getAllTasksByUser(pageNumber, pageSize, ordering, user.getId()), HttpStatus.OK);

    }

    /**
     * Return all sent tasks of user. Method for operator.
     *
     * @param pageNumber
     * @param pageSize
     * @param ordering
     * @param userName
     * @return
     */
    @GetMapping(value = "/operator/tasks")
    public ResponseEntity<PaginationResult<TaskResponse>> getSentTasksByUser(@RequestParam(required = false, defaultValue = "1") int pageNumber,
                                                                     @RequestParam(required = false, defaultValue = "5") int pageSize,
                                                                     @RequestParam(required = false, defaultValue = "desc") String ordering,
                                                                     @RequestParam(required = true) String userName) {

        return new ResponseEntity<>(taskService.getSentTasksByUser(pageNumber, pageSize, ordering, userName), HttpStatus.OK);

    }
    /**
     * All sent tasks. Method for operator.
     *
     * @param pageNumber
     * @param pageSize
     * @param ordering
     * @return
     */
    @GetMapping(value = "/operator/tasks/all")
    public ResponseEntity<PaginationResult<TaskResponse>>  getAllSentTask(@RequestParam(required = false, defaultValue = "1") int pageNumber,
                                                                  @RequestParam(required = false, defaultValue = "5") int pageSize,
                                                                  @RequestParam(required = false, defaultValue = "desc") String ordering) {

        return new ResponseEntity<>(taskService.getAllSentTask(pageNumber, pageSize, ordering), HttpStatus.OK);

    }


    /**
     * Create task. Method for users.
     * @param taskRequest
     * @return
     */
    @PostMapping(value = "/user/tasks/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest taskRequest) {

        Task task = taskService.createTask(taskRequest);
        TaskResponse taskResponse = new TaskResponse(task.getId(), task.getAuthor(), task.getStatus(), task.getText(), task.getDate());
        return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
    }


    /**
     * Edit text of task in draft status. Method for users.
     * @param taskRequest
     * @return
     */
    @PutMapping(value = "/user/tasks/edit")
    public ResponseEntity<?> updateTask(@RequestBody TaskUpdateRequest taskRequest) {
        if(!taskRequest.getStatus().equals("draft")){
            return new ResponseEntity<>("wrong status", HttpStatus.BAD_REQUEST);
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskRequest.setAuthor(user.getId());
        Task task = taskService.getTaskById(taskRequest.getId());
        task.setText(taskRequest.getText());
        return new ResponseEntity<>(taskService.updateTask(task), HttpStatus.OK);

    }


    /**
     * Change task status to sent. Method for users.
     * @param taskRequest
     * @return
     */
    @PutMapping(value = "/user/tasks/send")
    public ResponseEntity<?> sendTask(@RequestBody TaskUpdateRequest taskRequest) {
        if(!taskRequest.getStatus().equals("sent")){
            return new ResponseEntity<>("wrong status", HttpStatus.BAD_REQUEST);
        }
        Task task = taskService.getTaskById(taskRequest.getId());
        return new ResponseEntity<>(taskService.updateTask(taskService.changeTaskStatus(task, taskRequest.getStatus())), HttpStatus.OK);

    }

    /**
     * Change task status to accepted. Method for operator.
     * @param taskRequest
     * @return
     */
    @PutMapping(value = "/operator/tasks/accept")
    public ResponseEntity<?> acceptTask(@RequestBody TaskUpdateRequest taskRequest) {
        if(!taskRequest.getStatus().equals("sent")){
            return new ResponseEntity<>("wrong status", HttpStatus.BAD_REQUEST);
        }
        Task task = taskService.getTaskById(taskRequest.getId());
        return new ResponseEntity<>(taskService.updateTask(taskService.changeTaskStatus(task, taskRequest.getStatus())), HttpStatus.OK);

    }


    /**
     * Change task status to rejected. Method for operator.
     * @param taskRequest
     * @return
     */
    @PutMapping(value = "/operator/tasks/reject")
    public ResponseEntity<?> rejectTask(@RequestBody TaskUpdateRequest taskRequest) {
        if(!taskRequest.getStatus().equals("sent")){
            return new ResponseEntity<>("wrong status", HttpStatus.BAD_REQUEST);
        }
        Task task = taskService.getTaskById(taskRequest.getId());
        return new ResponseEntity<>(taskService.updateTask(taskService.changeTaskStatus(task, taskRequest.getStatus())), HttpStatus.OK);

    }




}
