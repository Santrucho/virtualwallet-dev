package com.santrucho.virtualwalletdev.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.santrucho.virtualwalletdev.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User)

    @Query("UPDATE USER_TABLE SET balance = balance - :amount WHERE cbu = :cbu")
    suspend fun sendMoney(cbu:Int,amount:Int)

    @Query("UPDATE USER_TABLE SET balance = balance + :amount WHERE cbu = :cbu")
    suspend fun depositMoney(cbu:Int,amount:Int)

}