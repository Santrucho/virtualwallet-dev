package com.santrucho.virtualwalletdev.data.model

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.santrucho.virtualwalletdev.utils.Constants.CARDS_TABLE


@Entity (tableName = CARDS_TABLE)
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
    val expiration : String = "",
    val type : Int = 0,
    val owner : String = ""
) {
}