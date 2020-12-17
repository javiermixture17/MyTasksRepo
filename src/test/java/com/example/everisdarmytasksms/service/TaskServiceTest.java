package com.example.everisdarmytasksms.service;

import com.example.everisdarmytasksms.domain.Task;
import com.example.everisdarmytasksms.exception.ResourceNotFoundException;
import com.example.everisdarmytasksms.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for Business Layer
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceImpl service;

    @Test
    public void retrieveAllTasksTest() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Task(1L,"Description 1", Task.TaskStatus.IN_PROGRESS),
                new Task(2L,"Description 2", Task.TaskStatus.FINISHED),
                new Task(3L,"Description 3", Task.TaskStatus.PENDING)));

        List<Task> tasks = service.retrieveAllTasks();

        verify(repository, times(1)).findAll();
        assert(tasks.get(0).equals(new Task(1L,"Description 1", Task.TaskStatus.IN_PROGRESS)));
        assert(tasks.get(1).equals(new Task(2L,"Description 2", Task.TaskStatus.FINISHED)));
        assert(tasks.get(2).equals(new Task(3L,"Description 3", Task.TaskStatus.PENDING)));
    }

    @Test
    public void retrieveTaskTest_OK() throws ResourceNotFoundException {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(new Task(1L, "Description 1", Task.TaskStatus.IN_PROGRESS)));

        Task task = service.retrieveTask(1L);

        verify(repository, times(1)).findById(1L);
        assert(task.equals(new Task(1L,"Description 1", Task.TaskStatus.IN_PROGRESS)));
    }

    @Test
    public void retrieveTaskTest_BAD() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            Task task = service.retrieveTask(1L);
        });

        String expectedMessage = "Task not found with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createTaskTest() {
        when(repository.save(any(Task.class))).thenReturn(new Task(1L, "Description 1", Task.TaskStatus.IN_PROGRESS));

        Task task = service.createTask(new Task(1L, "Description 1", Task.TaskStatus.IN_PROGRESS));

        verify(repository, times(1)).save(any(Task.class));
        assert(task.equals(new Task(1L,"Description 1", Task.TaskStatus.IN_PROGRESS)));
    }

    @Test
    public void replaceTaskTest() {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(new Task(1L, "Description 1", Task.TaskStatus.IN_PROGRESS)));
        when(repository.save(any(Task.class))).thenReturn(new Task(1L, "Description 1 updated", Task.TaskStatus.IN_PROGRESS));

        Task task = service.replaceTask(new Task(1L, "Description 1 updated", Task.TaskStatus.IN_PROGRESS), 1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Task.class));
        assert(task.equals(new Task(1L,"Description 1 updated", Task.TaskStatus.IN_PROGRESS)));
    }

    @Test
    public void deleteTaskTest_OK() throws ResourceNotFoundException {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(new Task(1L, "Description 1", Task.TaskStatus.IN_PROGRESS)));

        service.deleteTask(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);

    }

    @Test
    public void deleteTaskTest_BAD()  {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteTask(1L);
        });

        String expectedMessage = "Task not found with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repository, times(0)).deleteById(1L);
    }

}