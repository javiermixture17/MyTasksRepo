package com.example.everisdarmytasksms.service;

import com.example.everisdarmytasksms.domain.Task;
import com.example.everisdarmytasksms.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Task Service.
 */
public interface TaskService {

    /**
     * Get All Tasks.
     * @return List of all tasks.
     */
    List<Task> retrieveAllTasks();

    /**
     * Get Task By Id.
     * @param id Id
     * @return Task
     */
    Task retrieveTask(Long id) throws ResourceNotFoundException;

    /**
     * Create Task.
     * @param newTask Task to create
     * @return Created Task
     */
    Task createTask(Task newTask);

    /**
     * Replace Task.
     * @param id Id
     * @param newTask Task to replace
     * @return Replaced Task
     */
    Task replaceTask(Task newTask, Long id);

    /**
     * Delete Task by Id.
     * @param id Id
     */
    void deleteTask(Long id) throws ResourceNotFoundException;

}
