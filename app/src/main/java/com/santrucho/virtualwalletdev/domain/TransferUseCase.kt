package com.santrucho.virtualwalletdev.domain

import com.santrucho.virtualwalletdev.repository.UserRepository
import com.santrucho.virtualwalletdev.utils.Resource
import javax.inject.Inject

class TransferUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(addresseeCbu: String,
                                senderCbu: String,
                                amount: Int?) : Resource<Int>{
        return try{
            userRepository.transferMoney(addresseeCbu,senderCbu,amount)
        }catch (e:Exception){
            Resource.Failure(e)
        }
    }
}