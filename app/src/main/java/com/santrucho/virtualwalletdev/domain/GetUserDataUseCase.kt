package com.santrucho.virtualwalletdev.domain

import com.santrucho.virtualwalletdev.data.model.User
import com.santrucho.virtualwalletdev.repository.UserRepository
import com.santrucho.virtualwalletdev.utils.Resource
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(username:String) : Resource<User?>{
        return try{
            userRepository.getUserData(username)
        }catch (e:Exception){
            Resource.Failure(e)
        }
    }
}