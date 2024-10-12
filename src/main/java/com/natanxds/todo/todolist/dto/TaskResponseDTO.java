package com.natanxds.todo.todolist.dto;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        boolean isCompleted,
        String createdAt,
        String updatedAt
) {
}
