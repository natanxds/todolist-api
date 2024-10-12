package com.natanxds.todo.todolist.controller;

import com.natanxds.todo.todolist.dto.TaskRequestDTO;
import com.natanxds.todo.todolist.dto.TaskResponseDTO;
import com.natanxds.todo.todolist.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // Angular dev server URL
@RestController
@RequestMapping("api/v1/tasks")
@Validated
@Log
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        log.info("GET /api/v1/tasks");
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") Long id) {
        log.info("GET /api/v1/tasks/" + id);
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        log.info("POST /api/v1/tasks");
        return ResponseEntity.created(null).body(taskService.save(taskRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        log.info("PUT /api/v1/tasks/" + id);
        return taskService.findById(id)
                .map(existingTask -> {
                    TaskResponseDTO updatedTask = taskService.updateTask(id, taskRequestDTO);
                    return ResponseEntity.ok(updatedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("DELETE /api/v1/tasks/" + id);
        if (taskService.findById(id).isPresent()) {
            taskService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
