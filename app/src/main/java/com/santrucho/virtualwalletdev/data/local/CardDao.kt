package com.santrucho.virtualwalletdev.data.local

import androidx.room.*
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.utils.Constants.CARDS_TABLE

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(product: Card) : Long

    @Delete
    fun deleteCard(product:Card)

    @Query("SELECT * FROM $CARDS_TABLE ORDER BY uid ASC")
    suspend fun getAllCard() : List<Card>

    @Query("SELECT * FROM $CARDS_TABLE WHERE uid = :productId")
    suspend fun getCard(productId:Int) : Card
}