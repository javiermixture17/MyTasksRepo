package com.example.everisdarmytasksms.repository;

import com.example.everisdarmytasksms.domain.Task;
import com.example.everisdarmytasksms.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests for Data Layer
 *
 * Previously inserted the following data (data.sql):
 * insert into task(description,status)values('buy cereal','IN_PROGRESS');
 * insert into task(description,status)values('study math','IN_PROGRESS');
 */
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    @Test
    public void findAllTest() {
        List<Task> tasks = repository.findAll();
        assertEquals(2, tasks.size());
    }

    @Test
    public void findByIDTest() throws ResourceNotFoundException {
        Optional<Task> task_opt = repository.findById(1l);
        if(task_opt.isPresent()) {
            Task task = task_opt.get();
            assert(task.equals(new Task(1L,"buy cereal", Task.TaskStatus.IN_PROGRESS)));
        }else {
            throw new ResourceNotFoundException("Task not found");
        }
    }

    @Test
    public void saveTest() throws ResourceNotFoundException {
        Task insert_task = new Task(3L,"Task 3", Task.TaskStatus.IN_PROGRESS);
        Task task = repository.save(insert_task);

        assert(task.equals(insert_task));

        List<Task> tasks = repository.findAll();
        assertEquals(3, tasks.size());

        Optional<Task> task_opt = repository.findById(3l);
        if(task_opt.isPresent()) {
            Task task2 = task_opt.get();
            assert(task2.equals(insert_task));
        }else {
            throw new ResourceNotFoundException("Task not found");
        }

    }

    @Test
    public void deleteTest() {
        repository.deleteById(1l);
        Optional<Task> task_opt = repository.findById(1l);
        assertFalse(task_opt.isPresent());
    }
}
