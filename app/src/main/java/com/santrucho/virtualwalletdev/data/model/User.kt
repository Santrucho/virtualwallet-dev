package com.santrucho.virtualwalletdev.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.santrucho.virtualwalletdev.utils.Constants.USER_TABLE

@Entity(tableName=USER_TABLE)
data class User(
    @PrimaryKey
    val cbu : String = "",
    val username : String = "",
    val balance : Int = 0
) {
}