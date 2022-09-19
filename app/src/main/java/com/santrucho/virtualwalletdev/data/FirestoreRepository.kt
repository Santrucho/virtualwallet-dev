package com.santrucho.virtualwalletdev.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.virtualwalletdev.model.Card
import com.santrucho.virtualwalletdev.utils.Resource
import kotlinx.coroutines.tasks.await

interface FirestoreRepository{

    suspend fun getDataFromFirestore() : Resource.Success<MutableList<Card>>

}