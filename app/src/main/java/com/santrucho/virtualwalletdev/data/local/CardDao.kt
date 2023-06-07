package com.santrucho.virtualwalletdev.data.local

import androidx.room.*
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.utils.Constants.CARDS_TABLE

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: Card) : Long

    @Delete
    fun deleteCard(card:Card)

    @Query("SELECT * FROM $CARDS_TABLE WHERE owner = :owner ORDER BY uid ASC")
    suspend fun getAllCard(owner:String) : List<Card>

    @Query("SELECT * FROM $CARDS_TABLE WHERE uid = :cardId")
    suspend fun getCard(cardId:Int) : Card
}