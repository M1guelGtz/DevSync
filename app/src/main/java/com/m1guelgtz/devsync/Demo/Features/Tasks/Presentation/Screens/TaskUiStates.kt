package com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Presentation.Screens

import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Domain.Entities.Task

data class TaskListUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val filterStatus: String? = null,
    val showCreateDialog: Boolean = false
)

data class TaskDetailUiState(
    val isLoading: Boolean = true,
    val task: Task? = null,
    val error: String? = null,
    val showEditDialog: Boolean = false,
    val showDeleteConfirmation: Boolean = false
)

data class CreateTaskFormState(
    val title: String = "",
    val description: String = "",
    val dueDate: String? = null,
    val priority: String = "medium",
    val titleError: String? = null,
    val descriptionError: String? = null
)
