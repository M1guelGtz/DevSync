package com.m1guelgtz.templatecarsapi.Demo.Features.Projects.Presentation.Screens

import com.m1guelgtz.templatecarsapi.Demo.Features.Projects.Domain.Entities.Project

data class ProjectListUiState(
    val isLoading: Boolean = false,
    val projects: List<Project> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val showCreateDialog: Boolean = false
)

data class ProjectDetailUiState(
    val isLoading: Boolean = true,
    val project: Project? = null,
    val members: List<String> = emptyList(),
    val error: String? = null,
    val showAddMemberDialog: Boolean = false,
    val showEditDialog: Boolean = false
)

data class CreateProjectFormState(
    val name: String = "",
    val description: String = "",
    val nameError: String? = null,
    val descriptionError: String? = null
)
