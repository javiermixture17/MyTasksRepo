package com.example.everisdarmytasksms.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * Task Entity Class.
 */
@Entity
public class Task {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * Task description.
     */
    private String description; //Establecer límite de 256 - Usar un char


    /**
     * Task status.
     */
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    public enum TaskStatus {
        IN_PROGRESS,
        PENDING,
        FINISHED
    }

    protected Task() {}

    /**
     * Task constructor.
     */
    public Task(Long id, String description, TaskStatus status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    /**
     * Get id.
     * @return Id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get description.
     * @return Description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get status.
     * @return Status.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Set Id.
     * @param id Id of the Task
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Set description.
     * @param description Description of the Task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set status.
     * @param status Status of the Task
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Task))
            return false;
        Task task = (Task) o;
        return Objects.equals(this.id, task.id) && Objects.equals(this.description, task.description)
                && Objects.equals(this.status, task.status);
    }

}
