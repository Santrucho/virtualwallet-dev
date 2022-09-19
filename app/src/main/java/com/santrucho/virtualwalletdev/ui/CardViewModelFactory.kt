package com.santrucho.virtualwalletdev.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.santrucho.virtualwalletdev.domain.CardUseCase

class CardViewModelFactory(private val useCase : CardUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CardUseCase::class.java).newInstance(useCase)
    }
}