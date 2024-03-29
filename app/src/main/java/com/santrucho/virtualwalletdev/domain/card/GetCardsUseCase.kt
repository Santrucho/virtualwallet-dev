package com.santrucho.virtualwalletdev.domain.card

import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.repository.card.CardRepository
import com.santrucho.virtualwalletdev.utils.Resource
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val cardRepository: CardRepository) {

    suspend operator fun invoke(owner:String) : Resource<List<Card>> {
        return try{
            cardRepository.getCards(owner)
        } catch (e:Exception){
            Resource.Failure(e)
        }
    }
}