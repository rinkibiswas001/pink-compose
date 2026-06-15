package com.pinkcompose.data.repository

import com.pinkcompose.data.local.DataStoreManager
import com.pinkcompose.data.mapper.toDomain
import com.pinkcompose.data.remote.ApiService
import com.pinkcompose.domain.model.Login
import com.pinkcompose.domain.repository.AuthRepository
import com.pinkcompose.util.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Resource<Login> {

        return try {

            val response = apiService.getUsers()

            if (response.isSuccessful) {

                val users = response.body().orEmpty()

                val user = users.find {
                    it.email == email &&
                            it.password == password
                }

                if (user != null) {
                    val domainUser = user.toDomain()
                    dataStoreManager.saveUser(domainUser)
                    Resource.Success(domainUser)
                } else {
                    Resource.Error("Invalid email or password")
                }

            } else {
                Resource.Error(response.message())
            }

        } catch (e: Exception) {
            Resource.Error(
                e.message ?: "An unknown error occurred"
            )
        }
    }

}
