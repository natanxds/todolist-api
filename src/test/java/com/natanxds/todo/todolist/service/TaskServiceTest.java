package com.natanxds.todo.todolist.service;

import com.natanxds.todo.todolist.dto.TaskRequestDTO;
import com.natanxds.todo.todolist.dto.TaskResponseDTO;
import com.natanxds.todo.todolist.model.Task;
import com.natanxds.todo.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsListOfTasks() {
        Task task = new Task(1L, "Title", "Description", false, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskResponseDTO> tasks = taskService.findAll();

        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void findById_existingId_returnsTask() {
        Long id = 1L;
        Task task = new Task(id, "Title", "Description", false, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        Optional<TaskResponseDTO> result = taskService.findById(id);

        assertTrue(result.isPresent());
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    void findById_nonExistingId_returnsEmpty() {
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        Optional<TaskResponseDTO> result = taskService.findById(id);

        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    void updateTask_existingId_returnsUpdatedTask() {
        Long id = 1L;
        TaskRequestDTO request = new TaskRequestDTO("Updated Title", "Updated Description", true);
        Task task = request.toEntity();
        task.setId(id);
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);

        TaskResponseDTO result = taskService.updateTask(id, request);

        assertEquals("Updated Title", result.title());
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    void updateTask_nonExistingId_returnsNull() {
        Long id = 1L;
        TaskRequestDTO request = new TaskRequestDTO("Updated Title", "Updated Description", true);
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        TaskResponseDTO result = taskService.updateTask(id, request);

        assertNull(result);
        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteById_existingId_deletesTask() {
        Long id = 1L;
        Task task = new Task(id, "Title", "Description", false, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        taskService.deleteById(id);

        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteById_nonExistingId_doesNothing() {
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        taskService.deleteById(id);

        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, never()).deleteById(id);
    }
}