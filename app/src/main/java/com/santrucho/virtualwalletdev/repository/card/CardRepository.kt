package com.santrucho.virtualwalletdev.repository.card

import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.utils.Resource

interface CardRepository {

    suspend fun addCard(card: Card) : Resource<Card>

    suspend fun getCards(owner:String) : Resource<List<Card>>

    suspend fun deleteCard(card:Card)

    suspend fun getCard() : Card

}