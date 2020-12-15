package com.example.everisdarmytasksms.repository;

import com.example.everisdarmytasksms.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}