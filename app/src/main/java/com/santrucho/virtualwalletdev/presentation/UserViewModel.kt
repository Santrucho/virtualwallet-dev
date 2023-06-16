package com.santrucho.virtualwalletdev.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santrucho.virtualwalletdev.data.model.User
import com.santrucho.virtualwalletdev.domain.*
import com.santrucho.virtualwalletdev.utils.Resource
import com.santrucho.virtualwalletdev.utils.formatMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val transferUseCase: TransferUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<Resource<User>?>(null)
    val userState: StateFlow<Resource<User>?> = _userState

    private val _loginState = MutableStateFlow<Resource<User?>?>(null)
    val loginState: StateFlow<Resource<User?>?> = _loginState

    private val _userInfo = MutableStateFlow<Resource<User?>?>(null)
    val userInfo: StateFlow<Resource<User?>?> = _userInfo

    private val _sendMoney = MutableStateFlow<Resource<Int>?>(null)
    val sendMoney : StateFlow<Resource<Int>?> = _sendMoney

    fun createUser(user: User) {
        viewModelScope.launch {
            _userState.value = Resource.Loading()
            _userState.value = createUserUseCase(user)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            _loginState.value =  loginUseCase(username, password)
        }
    }
    fun getUserData(username:String){
        viewModelScope.launch{
            _userInfo.value = Resource.Loading()
            _userInfo.value = getUserDataUseCase(username)
        }
    }

    fun signOut(){
        viewModelScope.launch {
            _userState.value = Resource.Loading()
            _userState.value = null
            _loginState.value = Resource.Loading()
            _loginState.value = null
        }
    }

    fun transferMoney(addresseeCbu:String,senderCbu:String,amount:Int?){
        viewModelScope.launch {
            _sendMoney.value = Resource.Loading()
            _sendMoney.value = transferUseCase(addresseeCbu,senderCbu,amount)
        }
    }
}