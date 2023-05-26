package com.santrucho.virtualwalletdev.data.local

import androidx.room.*
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.utils.Constants.WALLET_TABLE

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(product: Card) : Long

    @Update
    fun updateCard(product:Card)

    @Delete
    fun deleteCard(product:Card)

    @Query("SELECT * FROM $WALLET_TABLE ORDER BY uid ASC")
    fun getAllCard() : List<Card>

    @Query("SELECT * FROM $WALLET_TABLE WHERE uid = :productId")
    fun getCard(productId:Int) : Card
}