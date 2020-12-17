package com.example.everisdarmytasksms.controller;

import com.example.everisdarmytasksms.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration Tests
 *
 * Previously inserted the following data (data.sql):
 * insert into task(description,status)values('buy cereal','IN_PROGRESS');
 * insert into task(description,status)values('study math','IN_PROGRESS');
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService service;

    @Test
    public void retrieveAllTasksTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/tasks")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:1,description:\"buy cereal\",status:IN_PROGRESS}," +
                        "{id:2,description:\"study math\",status:IN_PROGRESS}]"))
                .andReturn();
    }

    @Test
    public void retrieveTaskTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/tasks/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{id:1,description:\"buy cereal\",status:IN_PROGRESS}"))
                .andReturn();
    }

    @Test
    public void retrieveTaskTestNotFound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/tasks/3")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    /*
    @Test
    public void createTaskTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Description Test\", \"status\": \"IN_PROGRESS\"}");

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json("{id:3,description:\"Description Test\",status:IN_PROGRESS}"))
                .andReturn();

        RequestBuilder request2 = MockMvcRequestBuilders
                .get("/tasks/3")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result2 = mockMvc.perform(request2)
                .andExpect(status().isOk())
                .andExpect(content().json("{id:3,description:\"Description Test\",status:IN_PROGRESS}"))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/tasks/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }*/

    @Test
    public void replaceTaskTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .put("/tasks/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Description Test update\", \"status\": \"FINISHED\"}");

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json("{id:3,description:\"Description Test update\",status:FINISHED}"))
                .andReturn();

        RequestBuilder request2 = MockMvcRequestBuilders
                .get("/tasks/3")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result2 = mockMvc.perform(request2)
                .andExpect(status().isOk())
                .andExpect(content().json("{id:3,description:\"Description Test update\",status:FINISHED}"))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/tasks/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
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

        RequestBuilder request2 = MockMvcRequestBuilders
                .get("/tasks/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result2 = mockMvc.perform(request2)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteTaskTestNotFound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/tasks/5")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();

    }
}
