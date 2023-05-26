package com.santrucho.virtualwalletdev.repository

import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.utils.Resource

interface CardRepository {

    suspend fun addCard(card: Card) : Resource<Card>

    suspend fun getCards() : List<Card>

    suspend fun deleteCard(card:Card)

    suspend fun getCard() : Card

}