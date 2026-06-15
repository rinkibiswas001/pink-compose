package com.pinkcompose.domain.repository

import com.pinkcompose.domain.model.Login
import com.pinkcompose.util.Resource

interface AuthRepository {

    suspend fun login(email: String, password: String): Resource<Login>

}
