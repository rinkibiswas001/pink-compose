package com.pinkcompose.data.mapper

import com.pinkcompose.data.remote.dto.login.LoginResponse
import com.pinkcompose.domain.model.Login

fun LoginResponse.toDomain(): Login {
    return Login(
        id = id,
        username = name,
        email = email,
        password = password
    )
}
