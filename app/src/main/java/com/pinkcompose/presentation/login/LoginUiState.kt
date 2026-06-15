package com.pinkcompose.presentation.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val loginSuccess: Boolean = false,
    val error: String? = null
)
