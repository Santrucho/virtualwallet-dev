package com.santrucho.virtualwalletdev.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.santrucho.virtualwalletdev.utils.Constants.WALLET_TABLE

@Entity (tableName = WALLET_TABLE)
data class Card(
    @PrimaryKey
    val uid : String = "",
    @ColumnInfo(name = "card_name")
    val name : String = "",
    @ColumnInfo(name = "card_number")
    val number : String = "",
    @ColumnInfo(name = "card_code")
    val code : String = "",
    @ColumnInfo(name = "card_expiration")
    val expiration : String = ""
) {
}