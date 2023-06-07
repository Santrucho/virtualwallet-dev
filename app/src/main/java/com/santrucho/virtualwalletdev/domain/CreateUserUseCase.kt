package com.santrucho.virtualwalletdev.domain

import com.santrucho.virtualwalletdev.data.model.User
import com.santrucho.virtualwalletdev.repository.UserRepository
import com.santrucho.virtualwalletdev.utils.Resource
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(user:User) : Resource<User> {
        return try{
            userRepository.createUser(user)
        }catch (e:Exception){
            Resource.Failure(e)
        }
    }
}