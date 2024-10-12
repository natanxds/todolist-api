package com.natanxds.todo.todolist.service;

import com.natanxds.todo.todolist.dto.TaskRequestDTO;
import com.natanxds.todo.todolist.dto.TaskResponseDTO;
import com.natanxds.todo.todolist.model.Task;
import com.natanxds.todo.todolist.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskResponseDTO> findById(Long id) {
        return taskRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public TaskResponseDTO save(TaskRequestDTO taskRequestDTO) {
        Task savedTask = taskRepository.save(taskRequestDTO.toEntity());
        if (savedTask == null) {
            throw new IllegalStateException("Task could not be saved");
        }
        return convertToResponseDTO(savedTask);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isEmpty()) {
            return null;
        }
        Task task = taskRequestDTO.toEntity();
        task.setId(id);
        task = taskRepository.save(task);
        return convertToResponseDTO(task);
    }

    public void deleteById(Long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
        }
    }

    private TaskResponseDTO convertToResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME),
                task.getUpdatedAt().format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}