package com.santrucho.virtualwalletdev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.domain.movement.GetAllMovementUseCase
import com.santrucho.virtualwalletdev.domain.movement.NewMovementUseCase
import com.santrucho.virtualwalletdev.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovementViewModel @Inject constructor(private val newMovementUseCase: NewMovementUseCase,private val allMovementUseCase: GetAllMovementUseCase) : ViewModel() {

    private val _movementState = MutableStateFlow<Resource<Movement>?>(null)
    val movementState : StateFlow<Resource<Movement>?> = _movementState

    private val _allMovementState = MutableStateFlow<Resource<List<Movement>>?>(null)
    val allMovementState : StateFlow<Resource<List<Movement>>?> = _allMovementState

    init {
        getAllMovement()
    }

    fun generateMovement(movement:Movement){
        viewModelScope.launch {
            _movementState.value = Resource.Loading()
            _movementState.value = newMovementUseCase(movement)
        }
    }

    fun getAllMovement(){
        viewModelScope.launch {
            _allMovementState.value = Resource.Loading()
            _allMovementState.value = allMovementUseCase()
        }
    }

}