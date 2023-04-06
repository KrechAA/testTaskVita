package com.krech.vita.service;

import com.krech.vita.domain.rest.request.TaskCreateRequest;
import com.krech.vita.domain.rest.response.PaginationResult;
import com.krech.vita.domain.db.Task;
import com.krech.vita.domain.rest.response.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.krech.vita.repository.TaskRepositoryImpl;

import java.util.ArrayList;


@Service
public class TaskService {


    private final TaskRepositoryImpl taskRepositoryImpl;


    @Autowired
    public TaskService(TaskRepositoryImpl taskRepositoryImpl) {
        this.taskRepositoryImpl = taskRepositoryImpl;
    }


    /**
     * Creating task
     * @param taskRequest
     * @return
     */
    public Task createTask(TaskCreateRequest taskRequest) {
        Task task = new Task();
        task.setAuthor(taskRequest.getAuthor());
        task.setStatus(taskRequest.getStatus());
        task.setText(taskRequest.getText());
        task.setDate(taskRequest.getDate());

        return taskRepositoryImpl.createTask(task);
    }


    /**
     * This method returned all tasks of user.
     *
     * @param pageNumber
     * @param pageSize
     * @param ordering
     * @param userId
     * @return
     */
    public PaginationResult<Task> getAllTasksByUser(int pageNumber, int pageSize, String ordering, long userId) {
        return taskRepositoryImpl.getAllTasksByUser(pageNumber, pageSize, ordering, userId);
    }


    /**
     * Return all sent tasks. Method for operator.
     *
     * @param pageNumber
     * @param pageSize
     * @param ordering
     * @return
     */
    public PaginationResult<TaskResponse> getAllSentTask(int pageNumber, int pageSize, String ordering) {
        PaginationResult<Task> pR = taskRepositoryImpl.getAllSentTasks(pageNumber, pageSize, ordering);

        return preparePagResForOperator(pR);
    }

    /**
     * All sent tasks of user. Method for operator.
     */
    public PaginationResult<TaskResponse> getSentTasksByUser(int pageNumber, int pageSize, String ordering, String userName) {
        PaginationResult<Task> pR = taskRepositoryImpl.getSentTasksByUser(pageNumber, pageSize, ordering, userName);

        return preparePagResForOperator(pR);
    }


    /**
     * Searching task by id
     *
     * @param taskId
     * @return
     */
    public Task getTaskById(long taskId) {
        return taskRepositoryImpl.getTaskById(taskId);
    }

    /**
     * Changing task status
     *
     * @param task
     * @param newTaskStatus
     * @return
     */
    public Task changeTaskStatus(Task task, String newTaskStatus) {
        task.setStatus(newTaskStatus);
        return task;
    }


    /**
     * Updating task in repo
     *
     * @param task
     * @return
     */
    public Task updateTask(Task task) {
        return taskRepositoryImpl.updateTask(task);
    }

    /**
     * Adding dashes after characters in task text
     *
     * @param pR
     * @return
     */
    public PaginationResult<TaskResponse> preparePagResForOperator(PaginationResult<Task> pR) {
        PaginationResult<TaskResponse> pagResForClient = new PaginationResult<>();
        pagResForClient.setRecords(new ArrayList<>());

        for (int i = 0; i < pR.getRecords().size(); i++) {
            char[] charArr = pR.getRecords().get(i).getText().toCharArray();
            String[] strArr = new String[charArr.length];
            for (int j = 0; j < charArr.length; j++) {
                strArr[j] = String.valueOf(charArr[j]);
            }
            String result = String.join("-", strArr);

            TaskResponse taskResponse = new TaskResponse(pR.getRecords().get(i).getId(),
                    pR.getRecords().get(i).getAuthor(),
                    pR.getRecords().get(i).getStatus(),
                    result,
                    pR.getRecords().get(i).getDate());

            pagResForClient.getRecords().add(taskResponse);
        }


        /* for (int i = 0; i < pR.getRecords().size(); i++) {

            StringBuilder stringBuilder = new StringBuilder(pR.getRecords().get(i).getText());

            for (int j = 0; j < stringBuilder.length(); j++) {
                if (j == 0) {
                    stringBuilder.insert(j + 1, '-');
                    continue;
                }
                if (j == 1) {
                    continue;
                }
                if (j == stringBuilder.length() * 2 - 1) {
                    stringBuilder.insert(j + 1, '-');
                    break;
                }
                if (j % 2 == 0) {
                    stringBuilder.insert(j + 1, '-');
                }
            }
            String result = String.valueOf(stringBuilder);
            TaskResponse taskResponse = new TaskResponse(pR.getRecords().get(i).getId(),
                    pR.getRecords().get(i).getAuthor(),
                    pR.getRecords().get(i).getStatus(),
                    result,
                    pR.getRecords().get(i).getDate());

            pagResForClient.getRecords().add(taskResponse);
        }*/
        pagResForClient.setCurrentPageNumber(pR.getCurrentPageNumber());
        pagResForClient.setLastPageNumber(pR.getLastPageNumber());
        pagResForClient.setPageSize(pagResForClient.getPageSize());
        pagResForClient.setTotalRecords(pR.getTotalRecords());
        return pagResForClient;
    }
}
