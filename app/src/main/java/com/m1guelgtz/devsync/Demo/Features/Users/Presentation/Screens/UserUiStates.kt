package com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Screens

import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Domain.Entities.User

data class UserListUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)

data class UserDetailUiState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val error: String? = null
)
