package com.santrucho.virtualwalletdev.domain.movement

import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.repository.movement.MovementRepository
import com.santrucho.virtualwalletdev.utils.Resource
import javax.inject.Inject

class NewMovementUseCase @Inject constructor(private val movementRepository: MovementRepository) {

    suspend operator fun invoke(movement:Movement) : Resource<Movement>{
        return try{
            movementRepository.newMovement(movement)
        } catch (e:Exception) {
            Resource.Failure(e)
        }
    }
}