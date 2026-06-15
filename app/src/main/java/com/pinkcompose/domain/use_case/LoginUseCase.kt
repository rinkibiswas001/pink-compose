package com.pinkcompose.domain.use_case

import com.pinkcompose.domain.model.Login
import com.pinkcompose.domain.repository.AuthRepository
import com.pinkcompose.util.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<Login> {
        if (email.isBlank() || password.isBlank()) {
            return Resource.Error("Email or password cannot be empty")
        }
        return repository.login(email, password)
    }
}
