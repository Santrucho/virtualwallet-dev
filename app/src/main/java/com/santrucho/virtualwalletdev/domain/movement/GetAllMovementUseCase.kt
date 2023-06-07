package com.santrucho.virtualwalletdev.domain.movement

import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.repository.movement.MovementRepository
import com.santrucho.virtualwalletdev.utils.Resource
import javax.inject.Inject

class GetAllMovementUseCase @Inject constructor(private val movementRepository: MovementRepository){

    suspend operator fun invoke(owner:String) : Resource<List<Movement>> {
        return try{
            movementRepository.getAllMovement(owner)
        }catch (e:Exception){
            Resource.Failure(e)
        }
    }
}