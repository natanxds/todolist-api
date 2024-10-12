package com.natanxds.todo.todolist.dto;

import com.natanxds.todo.todolist.model.Task;
import jakarta.validation.constraints.NotBlank;

public record TaskRequestDTO(
        @NotBlank(message = "Title is mandatory") String title,
        String description,
        boolean isCompleted
) {
    public Task toEntity() {
        return new Task(this.title, this.description, this.isCompleted);
    }
}
