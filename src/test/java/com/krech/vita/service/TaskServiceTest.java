package com.krech.vita.service;

import com.krech.vita.domain.db.Task;
import com.krech.vita.domain.rest.request.TaskCreateRequest;
import com.krech.vita.domain.rest.response.PaginationResult;
import com.krech.vita.domain.rest.response.TaskResponse;
import com.krech.vita.repository.TaskRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    TaskRepositoryImpl taskRepositoryImpl;

    TaskService taskService;

    @BeforeEach
    void init() {
        taskService = new TaskService(taskRepositoryImpl);
    }

    @Test
    void createTaskTest() {
        TaskCreateRequest taskRequest = new TaskCreateRequest(1, "draft", "ololo", new Date(12313516511L));

        taskService.createTask(taskRequest);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepositoryImpl, times(1)).createTask(captor.capture());
        assertEquals(taskRequest.getAuthor(), captor.getValue().getAuthor());
        assertEquals(taskRequest.getStatus(), captor.getValue().getStatus());
        assertEquals(taskRequest.getText(), captor.getValue().getText());
        assertEquals(taskRequest.getDate(), captor.getValue().getDate());
        assertEquals(taskRequest.getDate(), captor.getValue().getDate());
    }

    @Test
    void preparePagResForOperatorTest() {
        PaginationResult<Task> pRt = new PaginationResult<>();
        pRt.setCurrentPageNumber(1);
        pRt.setPageSize(5);
        pRt.setLastPageNumber(1);
        pRt.setTotalRecords(2);
        pRt.setRecords(new ArrayList<>());

        pRt.getRecords().add(new Task());
        pRt.getRecords().get(0).setId(1L);
        pRt.getRecords().get(0).setStatus("sent");
        pRt.getRecords().get(0).setText("ololo");
        pRt.getRecords().get(0).setAuthor(1L);
        pRt.getRecords().get(0).setDate(new Date(21321561656L));

        pRt.getRecords().add(new Task());
        pRt.getRecords().get(1).setId(2L);
        pRt.getRecords().get(1).setStatus("sent");
        pRt.getRecords().get(1).setText("ololo2");
        pRt.getRecords().get(1).setAuthor(2L);
        pRt.getRecords().get(1).setDate(new Date(21321561657L));

        PaginationResult<TaskResponse> pRtR = taskService.preparePagResForOperator(pRt);

        assertEquals("o-l-o-l-o", pRtR.getRecords().get(0).getText());
        assertEquals("o-l-o-l-o-2", pRtR.getRecords().get(1).getText());
        assertEquals(1L, pRtR.getRecords().get(0).getId());
        assertEquals(2L, pRtR.getRecords().get(1).getId());
        assertEquals("sent", pRtR.getRecords().get(0).getStatus());
        assertEquals("sent", pRtR.getRecords().get(1).getStatus());
        assertEquals(1L, pRtR.getRecords().get(0).getAuthor());
        assertEquals(2L, pRtR.getRecords().get(1).getAuthor());
        assertEquals(pRt.getRecords().get(0).getDate(), pRtR.getRecords().get(0).getDate());
        assertEquals(pRt.getRecords().get(1).getDate(), pRtR.getRecords().get(1).getDate());

    }
}