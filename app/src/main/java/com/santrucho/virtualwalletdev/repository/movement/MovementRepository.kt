package com.santrucho.virtualwalletdev.repository.movement

import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.utils.Resource

interface MovementRepository {

    suspend fun newMovement(movement: Movement) : Resource<Movement>

    suspend fun getAllMovement() : Resource<List<Movement>>
}