package com.santrucho.virtualwalletdev.data

import com.google.firebase.firestore.FirebaseFirestore
import com.santrucho.virtualwalletdev.model.Card
import com.santrucho.virtualwalletdev.utils.Resource
import kotlinx.coroutines.tasks.await

class FirestoreRepoImpl : FirestoreRepository {

    override suspend fun getDataFromFirestore(): Resource.Success<MutableList<Card>> {
        val cardList = mutableListOf<Card>()
        val resultData = FirebaseFirestore.getInstance()
            .collection("cards").get().await()

        for (document in resultData){
            val name = document.getString("name")
            val number = document.getString("number")
            val expiration = document.getString("expiration")
            val code = document.getString("code")
            cardList.add(Card(name!!,number!!,expiration!!,code!!))
        }

        return Resource.Success(cardList)
    }
}

