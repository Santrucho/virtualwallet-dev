package com.santrucho.virtualwalletdev.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.santrucho.virtualwalletdev.utils.Constants.MOVEMENTS_TABLE

@Entity(tableName = MOVEMENTS_TABLE)
data class Movement(
    @PrimaryKey
    var id : String = "",
    var operation : String = "",
    var date : String = "",
    var amount : Int = 0,
    var owner : String = ""
){
}