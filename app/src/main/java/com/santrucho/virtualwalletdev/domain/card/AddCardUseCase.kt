package com.santrucho.virtualwalletdev.domain.card

import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.repository.card.CardRepository
import com.santrucho.virtualwalletdev.utils.Resource
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val cardRepository: CardRepository) {

    suspend operator fun invoke(card:Card) : Resource<Card> {
        return try{
            cardRepository.addCard(card)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}