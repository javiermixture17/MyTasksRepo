package com.example.everisdarmytasksms.controller;

import com.example.everisdarmytasksms.domain.Task;
import com.example.everisdarmytasksms.exception.ResourceNotFoundException;
import com.example.everisdarmytasksms.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for Web layer - Controllers
 */
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    @Test
    public void retrieveAllTasksTest() throws Exception {
        when(service.retrieveAllTasks()).thenReturn(
                Arrays.asList(new Task(1L,"Description 1", Task.TaskStatus.IN_PROGRESS),
                        new Task(2L,"Description 2", Task.TaskStatus.FINISHED),
                        new Task(3L,"Description 3", Task.TaskStatus.PENDING)));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/tasks")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:1,description:\"Description 1\",status:IN_PROGRESS}," +
                        "{id:2,description:\"Description 2\",status:FINISHED}," +
                        "{id:3,description:\"Description 3\",status:PENDING}]"))
                .andReturn();
    }

    @Test
    public void retrieveTaskTest() throws Exception {
        when(service.retrieveTask(anyLong())).thenReturn(new Task(1L,"Description 1", Task.TaskStatus.IN_PROGRESS));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/tasks/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{id:1,description:\"Description 1\",status:IN_PROGRESS}"))
                .andReturn();
    }

    @Test
    public void retrieveTaskTestNotFound() throws Exception {
        when(service.retrieveTask(anyLong())).thenThrow(ResourceNotFoundException.class);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/tasks/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void createTaskTest() throws Exception {
        when(service.createTask(any(Task.class))).thenReturn(new Task(1L,"Description 1", Task.TaskStatus.IN_PROGRESS));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Description 1\", \"status\": \"IN_PROGRESS\"}");

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{id:1,description:\"Description 1\",status:IN_PROGRESS}"))
                .andReturn();

    }

    @Test
    public void replaceTaskTest() throws Exception {
        when(service.replaceTask(any(Task.class), anyLong())).thenReturn(new Task(1L,"Description 1 update", Task.TaskStatus.FINISHED));

        RequestBuilder request = MockMvcRequestBuilders
                .put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Description 1 update\", \"status\": \"FINISHED\"}");

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{id:1,description:\"Description 1 update\",status:FINISHED}"))
                .andReturn();

    }

    @Test
    public void deleteTaskTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/tasks/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deleteTaskTestNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class)
                .when(service)
                .deleteTask(anyLong());

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/tasks/1")
                .accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
