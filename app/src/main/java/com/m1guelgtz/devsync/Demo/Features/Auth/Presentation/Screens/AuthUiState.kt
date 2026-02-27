package com.m1guelgtz.templatecarsapi.Demo.Features.Auth.Presentation.Screens

import com.m1guelgtz.templatecarsapi.Demo.Features.Auth.Domain.Entities.User

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: User) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

data class RegisterFormState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)
