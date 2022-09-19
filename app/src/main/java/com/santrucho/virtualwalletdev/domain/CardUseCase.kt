package com.santrucho.virtualwalletdev.domain


import com.santrucho.virtualwalletdev.model.Card
import com.santrucho.virtualwalletdev.utils.Resource

interface CardUseCase {
    suspend fun getCards() : Resource<MutableList<Card>>
}