package com.santrucho.virtualwalletdev.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.santrucho.virtualwalletdev.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM USER_TABLE WHERE username = :username")
    suspend fun getUserData(username:String) : User?

    @Query("SELECT * FROM USER_TABLE WHERE email = :email")
    suspend fun getUserByEmail(email:String) : User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User) : Long

    @Query("UPDATE USER_TABLE SET balance = balance - :amount WHERE cbu = :cbu")
    suspend fun sendMoney(cbu:Int,amount:Int)

    @Query("UPDATE USER_TABLE SET balance = balance + :amount WHERE cbu = :cbu")
    suspend fun depositMoney(cbu:Int,amount:Int)

}