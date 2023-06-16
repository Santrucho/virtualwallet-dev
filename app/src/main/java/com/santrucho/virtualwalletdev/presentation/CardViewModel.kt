package com.santrucho.virtualwalletdev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.domain.card.AddCardUseCase
import com.santrucho.virtualwalletdev.domain.card.GetCardsUseCase
import com.santrucho.virtualwalletdev.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {

    val number = MutableStateFlow("")

    private var _cardState = MutableStateFlow<Resource<Card>?>(null)
    val cardState: StateFlow<Resource<Card>?> = _cardState

    private var _allCardsState = MutableStateFlow<Resource<List<Card>>?>(null)
    val allCardsState: StateFlow<Resource<List<Card>>?> = _allCardsState

    fun addCard(card: Card) {
        viewModelScope.launch {
            _cardState.value = Resource.Loading()
            _cardState.value = addCardUseCase(card)
        }
    }

    fun getAllCards(owner:String) {
        viewModelScope.launch {
            _allCardsState.value = Resource.Loading()
            _allCardsState.value = getCardsUseCase(owner)
        }
    }
}