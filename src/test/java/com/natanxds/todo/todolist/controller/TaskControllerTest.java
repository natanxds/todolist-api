package com.natanxds.todo.todolist.controller;

import com.natanxds.todo.todolist.dto.TaskRequestDTO;
import com.natanxds.todo.todolist.dto.TaskResponseDTO;
import com.natanxds.todo.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_returnsListOfTasks() {
        List<TaskResponseDTO> tasks = List.of(new TaskResponseDTO(1L, "Title", "Description", false, "2023-10-01", "2023-10-01"));
        when(taskService.findAll()).thenReturn(tasks);

        ResponseEntity<List<TaskResponseDTO>> response = taskController.getAllTasks();

        assertEquals(ResponseEntity.ok(tasks), response);
        verify(taskService, times(1)).findAll();
    }

    @Test
    void getTaskById_existingId_returnsTask() {
        Long id = 1L;
        TaskResponseDTO task = new TaskResponseDTO(1L, "Title", "Description", false, "2023-10-01", "2023-10-01");
        when(taskService.findById(id)).thenReturn(Optional.of(task));

        ResponseEntity<TaskResponseDTO> response = taskController.getTaskById(id);

        assertEquals(ResponseEntity.ok(task), response);
        verify(taskService, times(1)).findById(id);
    }

    @Test
    void getTaskById_nonExistingId_returnsNotFound() {
        Long id = 1L;
        when(taskService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<TaskResponseDTO> response = taskController.getTaskById(id);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(taskService, times(1)).findById(id);
    }

    @Test
    void createTask_validRequest_returnsCreatedTask() {
        TaskRequestDTO request = new TaskRequestDTO("Title", "Description", false);
        TaskResponseDTO task = new TaskResponseDTO(1L, "Title", "Description", false, "2023-10-01", "2023-10-01");
        when(taskService.save(request)).thenReturn(task);

        ResponseEntity<TaskResponseDTO> response = taskController.createTask(request);

        assertEquals(ResponseEntity.created(null).body(task), response);
        verify(taskService, times(1)).save(request);
    }

    @Test
    void updateTask_existingId_returnsUpdatedTask() {
        Long id = 1L;
        TaskRequestDTO request = new TaskRequestDTO("Title", "Description", false);
        TaskResponseDTO task = new TaskResponseDTO(1L, "Title", "Description", false, "2023-10-01", "2023-10-01");
        when(taskService.findById(id)).thenReturn(Optional.of(task));
        when(taskService.updateTask(id, request)).thenReturn(task);

        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(id, request);

        assertEquals(ResponseEntity.ok(task), response);
        verify(taskService, times(1)).findById(id);
        verify(taskService, times(1)).updateTask(id, request);
    }

    @Test
    void updateTask_nonExistingId_returnsNotFound() {
        Long id = 1L;
        TaskRequestDTO request = new TaskRequestDTO("Title", "Description", false);
        when(taskService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(id, request);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(taskService, times(1)).findById(id);
        verify(taskService, never()).updateTask(id, request);
    }

    @Test
    void deleteTask_existingId_returnsNoContent() {
        Long id = 1L;
        TaskResponseDTO task = new TaskResponseDTO(1L, "Title", "Description", false, "2023-10-01", "2023-10-01");
        when(taskService.findById(id)).thenReturn(Optional.of(task));

        ResponseEntity<Void> response = taskController.deleteTask(id);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(taskService, times(1)).findById(id);
        verify(taskService, times(1)).deleteById(id);
    }

    @Test
    void deleteTask_nonExistingId_returnsNotFound() {
        Long id = 1L;
        when(taskService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = taskController.deleteTask(id);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(taskService, times(1)).findById(id);
        verify(taskService, never()).deleteById(id);
    }
}