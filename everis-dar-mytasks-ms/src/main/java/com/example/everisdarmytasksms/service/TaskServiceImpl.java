package com.example.everisdarmytasksms.service;

import com.example.everisdarmytasksms.domain.Task;
import com.example.everisdarmytasksms.exception.ResourceNotFoundException;
import com.example.everisdarmytasksms.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Task Service Implementation.
 */
@Service
public class TaskServiceImpl implements TaskService{

    /**
     * Task Repository.
     */
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * Get All Tasks.
     * @return List of all tasks.
     */
    @Override
    public List<Task> retrieveAllTasks(){
        return repository.findAll();
    }

    /**
     * Get Task By Id.
     * @param id Id
     * @return Task
     */
    @Override
    public Task retrieveTask(Long id) throws ResourceNotFoundException {
        Optional<Task> task = repository.findById(id);
        if(task.isPresent()) {
            return task.get();
        }else {
            throw new ResourceNotFoundException("Task not found with id : " + id);
        }
    }

    /**
     * Create Task.
     * @param newTask Task to create
     * @return Created Task
     */
    @Override
    public Task createTask(Task newTask){
        return repository.save(newTask);
    }


    /**
     * Replace Task.
     * @param id Id
     * @param newTask Task to replace
     * @return Replaced Task
     */
    @Override
    public Task replaceTask(Task newTask, Long id){
        return repository.findById(id)
                .map(task -> {
                    task.setDescription(newTask.getDescription());
                    task.setStatus(newTask.getStatus());
                    return repository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return repository.save(newTask);
                });
    }

    /**
     * Delete Task by Id.
     * @param id Id
     */
    @Override
    public void deleteTask(Long id) throws ResourceNotFoundException {
        Optional<Task> task = repository.findById(id);

        if(task.isPresent()) {
            repository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Task not found with id : " + id);
        }
    }
}
