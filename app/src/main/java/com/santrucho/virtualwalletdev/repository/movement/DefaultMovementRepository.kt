package com.santrucho.virtualwalletdev.repository.movement

import com.santrucho.virtualwalletdev.data.local.MovementDao
import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultMovementRepository @Inject constructor(private val movementDao : MovementDao) : MovementRepository {
    override suspend fun newMovement(movement: Movement): Resource<Movement> {
        return withContext(Dispatchers.IO){
            try{
                val resultId = movementDao.addMovement(movement)
                val movementWithId = movement.copy(id = resultId.toString())
                Resource.Success(movementWithId)
            } catch (e:Exception){
                Resource.Failure(e)
            }
        }
    }

    override suspend fun getAllMovement(owner:String): Resource<List<Movement>> {
        return withContext(Dispatchers.IO){
            try{
                val result = movementDao.getAllMovement(owner)
                Resource.Success(result)
            }catch (e:Exception){
                Resource.Failure(e)
            }
        }
    }
}