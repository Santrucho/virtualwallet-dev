package com.santrucho.virtualwalletdev.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.utils.Constants.MOVEMENTS_TABLE

@Dao
interface MovementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovement(movement: Movement) : Long

    @Query("SELECT * FROM $MOVEMENTS_TABLE ORDER BY id ASC")
    suspend fun getAllMovement() : List<Movement>

}