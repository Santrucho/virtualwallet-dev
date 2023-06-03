package com.santrucho.virtualwalletdev.repository.card

import com.santrucho.virtualwalletdev.data.local.CardDao
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultCardRepository @Inject constructor(private val cardDao: CardDao) : CardRepository {
    override suspend fun addCard(card: Card) : Resource<Card> {
        return withContext(Dispatchers.IO){
            try{
                val cardId = cardDao.addCard(card)
                val cardWithId = card.copy(uid = cardId.toString())
                Resource.Success(cardWithId)
            } catch (e:Exception){
                Resource.Failure(e)
            }
        }
    }

    override suspend fun getCards(): Resource<List<Card>> {
        return withContext(Dispatchers.IO){
            try{
                Resource.Success(cardDao.getAllCard())
            } catch (e:Exception){
                Resource.Failure(e)
            }
        }
    }

    override suspend fun deleteCard(card: Card) {
        TODO("Not yet implemented")
    }

    override suspend fun getCard(): Card {
        TODO("Not yet implemented")
    }

}