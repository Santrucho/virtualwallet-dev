package com.santrucho.virtualwalletdev.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.santrucho.virtualwalletdev.data.FirestoreRepository
import com.santrucho.virtualwalletdev.domain.CardUseCase
import com.santrucho.virtualwalletdev.utils.Resource
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel(private val useCase : CardUseCase) : ViewModel() {

    val fetchCardList = liveData(Dispatchers.IO){
        try {
            val cardList = useCase.getCards()
            emit(cardList)
        }
        catch (e:Exception){
            emit(Resource.Failure(e.cause!! as Exception))
        }
    }
}