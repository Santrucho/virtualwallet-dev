package com.santrucho.virtualwalletdev.repository

import com.santrucho.virtualwalletdev.data.model.User
import com.santrucho.virtualwalletdev.utils.Resource

interface UserRepository {

    suspend fun createUser(user:User) : Resource<User>

    suspend fun loginUser(username:String,password:String) : Resource<User?>

    suspend fun getUserData(username:String) : Resource<User?>

}