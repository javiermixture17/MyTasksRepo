package com.example.everisdarmytasksms.controller;

import com.example.everisdarmytasksms.domain.Task;
import com.example.everisdarmytasksms.exception.ResourceNotFoundException;
import com.example.everisdarmytasksms.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Tasks REST Controller.
 */

@RestController
public class TaskController {

    /**
     * Task Service.
     */
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    /**
     * get endpoint to return list of all tasks.
     * @return List of Tasks
     */
    @GetMapping("/tasks")
    //TODO @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Task>> retrieveAllTasks() {
        //return new ResponseEntity<>(service.retrieveAllTasks(), HttpStatus.OK);
        return ResponseEntity.ok().body(service.retrieveAllTasks());
    }

    /**
     * get endpoint to return the Task by Id.
     * @param id Id
     * @return Task
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> retrieveTask(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(service.retrieveTask(id));
    }

    /**
     * Endpoint to create new Task.
     * @param newTask Task to add
     * @return Created Task
     */
    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
        URI location = URI.create(String.format("/tasks/%d", newTask.getId()));
        return ResponseEntity.created(location).body(service.createTask(newTask));
    }

    /**
     * Endpoint to update an existing Task or create a new one if it does not exist.
     * @param id Id to update
     * @param newTask Task content
     * @return Updated or Created Task
     */
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> replaceTask(@PathVariable Long id, @RequestBody Task newTask) {
        URI location = URI.create(String.format("/tasks/%d", newTask.getId()));
        return ResponseEntity.created(location).body(service.replaceTask(newTask, id));
    }

    /**
     * Endpoint to delete a Task.
     * @param id Id to delete
     * @return Success Message
     */
    @DeleteMapping("/tasks/{id}")
    public HttpStatus deleteTask(@PathVariable Long id) throws ResourceNotFoundException {
        this.service.deleteTask(id);
        //return new ResponseEntity<>("Success", HttpStatus.OK);
        return HttpStatus.OK;
    }
}
