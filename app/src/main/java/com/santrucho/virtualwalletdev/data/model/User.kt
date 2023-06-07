package com.santrucho.virtualwalletdev.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.santrucho.virtualwalletdev.utils.Constants.USER_TABLE

@Entity(tableName=USER_TABLE)
data class User(
    @PrimaryKey
    val cbu : String = "",
    val username : String = "",
    val email:String = "",
    val password : String = "",
    val balance : Long = 0L
) {
}