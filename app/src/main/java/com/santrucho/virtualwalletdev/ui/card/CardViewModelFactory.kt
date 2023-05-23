package com.santrucho.virtualwalletdev.ui.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CardViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}