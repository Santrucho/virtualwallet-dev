package com.santrucho.virtualwalletdev.domain

import com.santrucho.virtualwalletdev.data.FirestoreRepository
import com.santrucho.virtualwalletdev.model.Card
import com.santrucho.virtualwalletdev.utils.Resource

class CardUseCaseImpl(private val cardRepo : FirestoreRepository) : CardUseCase {

    override suspend fun getCards(): Resource<MutableList<Card>> = cardRepo.getDataFromFirestore()

}