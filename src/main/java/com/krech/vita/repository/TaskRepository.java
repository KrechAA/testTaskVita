package com.krech.vita.repository;

import com.krech.vita.domain.rest.response.PaginationResult;
import com.krech.vita.domain.db.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository {
    Task createTask(Task task);
     PaginationResult<Task> getAllTasksByUser(int pageNumber, int pageSize, String ordering, long userId);
     Task getTaskById(long taskId);
     Task updateTask(Task task);
     PaginationResult<Task> getAllSentTasks(int pageNumber, int pageSize, String ordering);
     PaginationResult<Task> getSentTasksByUser(int pageNumber, int pageSize, String ordering, String userName);
}
